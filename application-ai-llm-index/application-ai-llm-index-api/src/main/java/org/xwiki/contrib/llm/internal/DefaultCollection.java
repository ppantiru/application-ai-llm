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

import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import org.xwiki.contrib.llm.Collection;
import org.xwiki.contrib.llm.Document;
import org.xwiki.model.EntityType;
import org.xwiki.model.reference.EntityReference;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;

/**
 * Implementation of a {@code Collection} component.
 *
 * @version $Id$
 */
@Component(roles = DefaultCollection.class)
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
public class DefaultCollection implements Collection
{
    private static final String NAME_KEY = "name";
    private static final String PERMISSIONS_KEY = "permissions";
    private static final String EMBEDDINGMODEL_KEY = "embeddingModel";
    private static final String XCLASS = "AILLMApp.Collections.Code.CollectionsClass";
    
    @Inject 
    private Provider<XWikiContext> contextProvider;
    
    @Inject
    private Logger logger;

    private String name;
    private Map<String, Document> documents;
    private String permissions;
    private String embeddingModel;
    private XWikiDocument document;

    /**
     * Default constructor for DefaultCollection.
     * Initializes the collection name, permissions, embedding model, 
     * and empty document map.
     *
     * @param name The name of the collection.
     * @param permissions The permissions string for the collection. 
     * @param embeddingModel The embedding model used by the collection.
     */
    public void initialize(String name, String permissions, String embeddingModel)
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
    public boolean setName(String name)
    {
        if (name != null) {
            this.name = name;
            return true;
        }
        return false;
    }

    @Override
    public boolean setPermissions(String permissions)
    {
        if (permissions != null) {
            this.permissions = permissions;
            return true;
        }
        return false;
    }

    @Override
    public boolean setEmbeddingModel(String embeddingModel)
    {
        if (embeddingModel != null) {
            this.embeddingModel = embeddingModel;
            return true;
        }
        return false;
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

    // private DocumentReference getDocumentReference(String documentId)
    // {
    //     SpaceReference lastSpaceReference = this.document.getDocumentReference().getLastSpaceReference();
    //     SpaceReference documentSpace =  new SpaceReference("Documents", lastSpaceReference);
    //     return new DocumentReference(DigestUtils.sha256Hex(documentId), documentSpace);
    // }

    private String generateUniqueId()
    {
        // Implement a method to generate a unique ID
        // This is a placeholder implementation
        return "doc" + (documents.size() + 1);
    }
    
    @Override
    public Collection toCollection(XWikiDocument document)
    {
        this.document = document;
        EntityReference documentReference = this.document.getDocumentReference();
        EntityReference objectEntityReference = new EntityReference(
            XCLASS,
            EntityType.OBJECT,
            documentReference
        );
        BaseObject object = this.document.getXObject(objectEntityReference);
    
        // Pull collection properties from the xobject using constants
        this.name = object.getStringValue(NAME_KEY);
        this.permissions = object.getStringValue(PERMISSIONS_KEY);
        this.embeddingModel = object.getStringValue(EMBEDDINGMODEL_KEY);
    
        return this;
    }
    

    @Override
    public XWikiDocument toXWikiDocument(XWikiDocument document)
    {
        this.document = document;
        EntityReference documentReference = this.document.getDocumentReference();
        EntityReference objectEntityReference = new EntityReference(
            XCLASS,
            EntityType.OBJECT,
            documentReference
            );
        BaseObject object = this.document.getXObject(objectEntityReference);

        //update the xobject with the collection properties
        object.setStringValue(NAME_KEY, this.name);
        object.setStringValue(PERMISSIONS_KEY, this.permissions);
        object.setStringValue(EMBEDDINGMODEL_KEY, this.embeddingModel);

        //save xdocument
        XWikiContext context = this.contextProvider.get();
        try {
            context.getWiki().saveDocument(this.document, context);
        } catch (XWikiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return this.document;
    }
}

