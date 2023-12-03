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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.codec.digest.DigestUtils;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.contrib.llm.Collection;
import org.xwiki.contrib.llm.Document;
import org.xwiki.contrib.llm.IndexException;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.SpaceReference;
import org.xwiki.security.authorization.AccessDeniedException;
import org.xwiki.user.UserReference;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;

/**
 * Implementation of a {@code ICollection} component.
 *
 * @version $Id$
 */
@Component(roles = DefaultCollection.class)
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
public class DefaultCollection implements Collection
{

    @Inject 
    private Provider<XWikiContext> contextProvider;

    @Inject
    private Provider<DefaultDocument> documentProvider;

    private XWikiDocument document;

    /**
     * Handles the initialization of the component.
     * 
     * @param document
     */
    public void initialize(XWikiDocument document)
    {
        this.document = document;
    }

    @Override
    public Document getDocument(String documentId) throws IndexException
    {
        XWikiContext context = this.contextProvider.get();
        DocumentReference documentReference = getDocumentReference(documentId);
        try {
            XWikiDocument resultDocument = context.getWiki().getDocument(documentReference, context);
            if (!resultDocument.isNew()) {
                DefaultDocument result = this.documentProvider.get();
                result.initialize(resultDocument);
                return result;
            } else {
                return null;
            }
        } catch (XWikiException e) {
            throw new IndexException("Failed to get document " + documentId, e);
        }
    }

    private DocumentReference getDocumentReference(String documentId)
    {
        SpaceReference lastSpaceReference = this.document.getDocumentReference().getLastSpaceReference();
        SpaceReference documentSpace =  new SpaceReference("Documents", lastSpaceReference);
        return new DocumentReference(DigestUtils.sha256Hex(documentId), documentSpace);
    }
    
    @Override
    public String getName()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Override
    public List<String> getDocumentList(int offset, int count)
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDocumentList'");
    }


    @Override
    public List<UserReference> getUseUsers()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUseUsers'");
    }

    @Override
    public List<DocumentReference> getUseGroups()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUseGroups'");
    }

    @Override
    public List<UserReference> getManagerUsers()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getManagerUsers'");
    }

    @Override
    public List<DocumentReference> getManagerGroups()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getManagerGroups'");
    }

    @Override
    public List<UserReference> getAdminUsers()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAdminUsers'");
    }

    @Override
    public List<DocumentReference> getAdminGroups()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAdminGroups'");
    }

    @Override
    public String getChunkingMethod()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChunkingMethod'");
    }

    @Override
    public String getEmbeddingModel()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmbeddingModel'");
    }

    @Override
    public Document createDocument(String documentId) throws IndexException, AccessDeniedException
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createDocument'");
    }

    @Override
    public void removeDocument(String documentId) throws IndexException, AccessDeniedException
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeDocument'");
    }


}
