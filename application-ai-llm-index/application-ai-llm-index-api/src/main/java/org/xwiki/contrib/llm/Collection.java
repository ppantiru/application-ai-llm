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

import org.xwiki.component.annotation.Role;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.security.authorization.AccessDeniedException;
import org.xwiki.user.UserReference;


import java.util.List;

/**
 * Represents a collection in the AI-LLM indexing system.
 *
 * @version $Id$
 * @since 0.3
 */
@Role
public interface Collection 
{
    /**
     * Retrieves the name of the collection.
     *
     * @return the collection's name
     */
    String getName();

    /**
     * Retrieves the list of documents in the collection.
     *
     * @return a list of documents
     * @param offset the offset of the first document to retrieve
     * @param count the maximum number of documents to retrieve
     */
    List<String> getDocumentList(int offset, int count);

    /**
     * Retrieves a document from the collection.
     *
     * @return a document
     * @param documentId the document's identifier
     * @throws IndexException if the document cannot be retrieved
     */
    Document getDocument(String documentId) throws IndexException;

    /**
     * Retrieves the list users allowed to view the collection.
     *
     * @return a list of users
     */
    List<UserReference> getUseUsers();

    /**
     * Retrieves the list groups allowed to view the collection.
     *
     * @return a list of groups
     */
    List<DocumentReference> getUseGroups();

        /**
     * Retrieves the list users allowed to manage the collection.
     *
     * @return a list of users
     */
    List<UserReference> getManagerUsers();

    /**
     * Retrieves the list groups allowed to manage the collection.
     *
     * @return a list of groups
     */
    List<DocumentReference> getManagerGroups();

        /**
     * Retrieves the list users allowed to administrate the collection.
     *
     * @return a list of users
     */
    List<UserReference> getAdminUsers();

    /**
     * Retrieves the list groups allowed to administrate the collection.
     *
     * @return a list of groups
     */
    List<DocumentReference> getAdminGroups();

    /**
     * Retrieves the method used for chunking within the collection.
     *
     * @return the chunking method
     */
    String getChunkingMethod();

    /**
     * Retrieves the embedding model associated with the collection.
     *
     * @return the embedding model
     */
    String getEmbeddingModel();

    /**
     * Create a document within the collection.
     * Use UUID as document name OR a hash created from the document ID (hash would be preferable).
     * 
     * @return the document if the document was added to the collection, null otherwise
     * @param documentId the full name of the document
     * @throws IndexException if the document could not be added to the collection or if the document already exists
     */
    Document createDocument(String documentId) throws IndexException, AccessDeniedException;

    /**
     * Remove document from collection (also delete document: false/true).
     *
     * @param documentId the full name of the document
     * @throws IndexException if the document could not be removed from the collection
     */
    void removeDocument(String documentId) throws IndexException, AccessDeniedException;
    

}
