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
package org.xwiki.contrib.llm.script;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.script.service.ScriptService;

import org.xwiki.contrib.llm.Collection;
import org.xwiki.contrib.llm.Document;
import org.xwiki.contrib.llm.IndexException;
import org.xwiki.contrib.llm.internal.DefaultCollection;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Make the HelloWorld API available to scripting.
 *
 * @version $Id: 9d6e720e780c0f661812f7813756354dc8dde770 $
 */
@Component(roles = DefaultCollection.class)
@Named("collection")
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
public class CollectionScriptService implements ScriptService
{
    @Inject 
    private Collection collection;

    /**
     * Returns a document form the collection {@link Collection#getDocument(String documentId)}.
     * 
     * @param documentId the document identifier
     * @return the document
     */
    public Document getDocument(String documentId) throws IndexException
    {
        return this.collection.getDocument(documentId);
    }
}
