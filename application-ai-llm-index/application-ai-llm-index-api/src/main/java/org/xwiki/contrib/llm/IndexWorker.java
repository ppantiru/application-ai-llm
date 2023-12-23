/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.llm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.observation.EventListener;
import org.xwiki.observation.event.Event;
import org.xwiki.model.EntityType;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.model.reference.SpaceReference;
import org.xwiki.model.reference.SpaceReferenceResolver;

import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;

import com.xpn.xwiki.internal.event.XObjectAddedEvent;
import com.xpn.xwiki.internal.event.XObjectUpdatedEvent;
import com.xpn.xwiki.internal.event.XObjectPropertyUpdatedEvent;

import org.slf4j.Logger;
import org.xwiki.bridge.event.DocumentCreatingEvent;
import org.xwiki.bridge.event.DocumentUpdatedEvent;
import org.xwiki.component.annotation.Component;

/**
 * This class is responsible for handling the document queue,
 * chunking the documents from the queue, computing the embeddings and storing them in solr.
 * 
 * @version $Id$
 */
@Component
@Named("IndexWorker")
@Singleton
public class IndexWorker implements EventListener
{
    private static final String XCLASS_NAME = "KiDocumentsClass";
    private static final String XCLASS_SPACE_STRING = "AILLMApp.KiDocuments.Code";
 
    @Inject
    private Logger logger;

    @Inject
    @Named("current")
    private SpaceReferenceResolver<String> explicitStringSpaceRefResolver;

    //the queue of documents to be processed as map of documentID and document
    private Queue<String> linkedListDocumentQueue = new LinkedList<>();
    private boolean isProcessing;
    
    @Override public String getName()
    {
        return "IndexWorker";
    }
    
    @Override public List<Event> getEvents()
    {
        return Arrays.<Event>asList(new XObjectAddedEvent(), new XObjectUpdatedEvent(),
            new XObjectPropertyUpdatedEvent(), new DocumentCreatingEvent(), new DocumentUpdatedEvent());
    }

    @Override public void onEvent(Event event, Object source, Object data)
    {
        EntityReference documentClassReference = getObjectReference();
        this.logger.info("Event: {}", event);
        XWikiDocument document = (XWikiDocument) source;
        BaseObject documentObject = document.getXObject(documentClassReference);
        this.logger.info("Document: {}", document.getDocumentReference());
        if (documentObject != null) {
            try {
                //Add document to the queue
                String docID = documentObject.getStringValue("id");
                linkedListDocumentQueue.add(docID);
                this.logger.info("Document added to queue: {}", docID);
                this.logger.info("Queue size: {}", linkedListDocumentQueue.size());
                //if the queue is not empty and the worker is not processing, process the queue
                if (!linkedListDocumentQueue.isEmpty() && !isProcessing) {
                    isProcessing = true;
                    processDocumentQueue();
                    isProcessing = false;
                }
            } catch (Exception e) {
                this.logger.error("Failure in comment listener", e);
            }

        }
    }

    private void processDocumentQueue()
    {
        //while queue is not empty, get first document, log it's ID and remove it from the queue
        while (!linkedListDocumentQueue.isEmpty()) {
            String documentID = linkedListDocumentQueue.peek();
            this.logger.info("Processing document with ID: {}", documentID);
            //get Document from XWikiDocument using documentID
            
            linkedListDocumentQueue.remove();
        }
    }

    //get XObject reference for the collection XClass
    private EntityReference getObjectReference()
    {
        SpaceReference spaceRef = explicitStringSpaceRefResolver.resolve(XCLASS_SPACE_STRING);

        EntityReference collectionClassRef = new EntityReference(XCLASS_NAME,
                                    EntityType.DOCUMENT,
                                    spaceRef
                                );
        return new EntityReference(XCLASS_NAME, EntityType.OBJECT, collectionClassRef);
    }
}
