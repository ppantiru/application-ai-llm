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
import com.xpn.xwiki.doc.XWikiDocument;

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
     * Retrieves a document from the collection.
     *
     * @return a document
     * @param documentId the document's identifier
     * @throws IndexException if the document cannot be retrieved
     */
    Document getDocument(String documentId) throws IndexException;

    /**
     * Retrieves a document from the collection.
     *
     * @return a document
     * @param document XWiki document
     * @throws IndexException if the document cannot be retrieved
     */
    Document newDocument(XWikiDocument document) throws IndexException;

}
