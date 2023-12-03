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

/**
 * Represents a document within the WAISE collection in the AI-LLM indexing system.
 *
 * @version $Id$
 */
public interface Document 
{
    /**
     * Retrieves the unique identifier of the document.
     *
     * @return the document's ID
     */
    String getId();

    /**
     * Retrieves the collection the document belongs to.
     *
     * @return the collection the document belongs to
     */
    String getCollection();

    /**
     * Retrieves the title of the document.
     *
     * @return the document's title
     */
    String getTitle();

    /**
     * Retrieves the language of the document.
     *
     * @return the document's language
     */
    String getLanguage();

    /**
     * Retrieves the URL where the document is located.
     *
     * @return the document's URL
     */
    String getUrl();

    /**
     * Retrieves the MIME type of an attachment.
     *
     * @return the document's MIME type
     */
    String getMimeType();

    /**
     * Retrieves the content of the document.
     *
     * @return the document's content
     */
    String getContent();

    /**
     *  Generate embeddings for self.
     * 
     * @return embeddings
     */
    String generateEmbeddings();

    /**
    * Store embeddings in Solr.
    * @param embeddings to be stored in the dense vector format
    */
    void storeEmbeddings(String embeddings);
}
