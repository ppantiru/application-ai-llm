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
package org.xwiki.contrib.llm.internal;

import org.apache.commons.codec.digest.DigestUtils;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.context.Execution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import org.xwiki.contrib.llm.Collection;
import org.xwiki.contrib.llm.Document;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.SpaceReference;
import org.xwiki.contrib.llm.Converter;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;

import org.slf4j.Logger;

/**
 * Implementation of a {@code Collection} component.
 *
 * @version $Id$
 */
@Component(roles = DefaultDocument.class)
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
public class DefaultCollection implements Collection, Converter
{
    /**
     * The execution, to get the context from it.
     */
    @Inject
    protected Execution execution;
    
    private String name;
    private Map<String, Document> documents;
    private String permissions;
    private String embeddingModel;
    private XWikiDocument document;
    
    /**
     * The logger to log.
     */
    @Inject
    private Logger logger;

    @Inject
    private Provider<DefaultDocument> documentProvider;    

    /**
     * Default constructor for DefaultCollection.
     * Initializes the collection name, permissions, embedding model, 
     * and empty document map.
     *
     * @param name The name of the collection.
     * @param permissions The permissions string for the collection. 
     * @param embeddingModel The embedding model used by the collection.
     */
    public DefaultCollection(String name, String permissions, String embeddingModel)
    {
        this.name = name;
        this.permissions = permissions;
        this.embeddingModel = embeddingModel;
        this.documents = new HashMap<>();
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public List<Document> getDocumentList()
    {
        return new ArrayList<>(documents.values());
    }

    @Override
    public Document getDocument(String id)
    {
        return documents.get(id);
    }

    @Override
    public String getPermissions()
    {
        return permissions;
    }

    @Override
    public String getEmbeddingModel()
    {
        return embeddingModel;
    }

    @Override
    public boolean removeDocument(String id, boolean deleteDocument)
    {
        if (documents.containsKey(id)) {
            if (deleteDocument) {
                // Implement document deletion logic here if needed
            }
            documents.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void assignIdToDocument(Document document, String id)
    {
        // check if the document already exists
        documents.put(id, document);
    }

    @Override
    public Document createDocument() throws XWikiException
    {
        String uniqueId = generateUniqueId();
        Document newDocument = new DefaultDocument();
        newDocument.setID(uniqueId);
        documents.put(uniqueId, newDocument);
        return newDocument;
    }

    private DocumentReference getDocumentReference(String documentId)
    {
        SpaceReference lastSpaceReference = this.document.getDocumentReference().getLastSpaceReference();
        SpaceReference documentSpace =  new SpaceReference("Documents", lastSpaceReference);
        return new DocumentReference(DigestUtils.sha256Hex(documentId), documentSpace);
    }

    private String generateUniqueId()
    {
        // Implement a method to generate a unique ID
        // This is a placeholder implementation
        return "doc" + (documents.size() + 1);
    }

    /**
     * @return the xwiki context from the execution context
     */
    private XWikiContext getXContext()
    {
        return (XWikiContext) execution.getContext().getProperty("xwikicontext");
    }

    @Override
    public XWikiDocument toXWikiDocument(Collection collection)
    {
        return this.document;
    }

    @Override
    public Collection toCollection(XWikiDocument document)
    {
        this.document = document;
        return this;
    }
}

