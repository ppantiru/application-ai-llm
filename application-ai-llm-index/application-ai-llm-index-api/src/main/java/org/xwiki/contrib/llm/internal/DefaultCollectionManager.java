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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xwiki.contrib.llm.CollectionManager;

import org.xwiki.component.annotation.Component;

import javax.inject.Inject;
import javax.inject.Provider;

import javax.inject.Singleton;

import org.slf4j.Logger;

/**
 * Implementation of a {@code CollectionManager} component.
 *
 * @version $Id$
 */
@Component
@Singleton
public class DefaultCollectionManager implements CollectionManager
{
    
    @Inject
    private Logger logger;
    
    @Inject
    private Provider<DefaultCollection> collectionProvider;

    private Map<String, DefaultCollection> collections = new HashMap<>();

    
    @Override
    public DefaultCollection createCollection(String name, String permissions, String embeddingModel)
    {
        if (this.collections.containsKey(name)) {
            // Handle existing collection case
            this.logger.warn("Collection with name {} already exists", name);
            return null;
        } else {
            DefaultCollection newCollection = collectionProvider.get();
            newCollection.initialize(name, permissions, embeddingModel);
            this.collections.put(name, newCollection);
            return newCollection;
        }

    }
        
    @Override
    public List<DefaultCollection> listCollections()
    {
        return new ArrayList<>(collections.values());
    }
    
    @Override
    public DefaultCollection getCollection(String name)
    {
        return collections.get(name);
    }

    @Override
    public boolean deleteCollection(String name)
    {
        if (collections.containsKey(name)) {
            collections.remove(name);
            return true;
        }
        return false;
    }
}
