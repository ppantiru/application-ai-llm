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

import java.util.List;

import org.slf4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xwiki.contrib.llm.internal.DefaultCollection;
import org.xwiki.contrib.llm.internal.DefaultCollectionManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link DefaultCollectionManager}.
 *
 * @version $Id$
 */
@ExtendWith(MockitoExtension.class)
class DefaultCollectionManagerTest
{

    @InjectMocks
    private DefaultCollectionManager defaultCollectionManager;

    @Mock
    private Logger logger;


    @Test
    void testCreateCollectionWithNewName() {
        String name = "TestCollection";
        String permissions = "view, edit";
        String embeddingModel = "defaultModel";

        DefaultCollection result = defaultCollectionManager.createCollection(name, permissions, embeddingModel);

        assertNotNull(result, "Created collection should not be null");
        assertEquals(name, result.getName(), "Collection name should match");
        assertEquals(permissions, result.getPermissions(), "Collection permissions should match");
        assertEquals(embeddingModel, result.getEmbeddingModel(), "Collection embedding model should match");
    }

    @Test
    void testCreateCollectionWithExistingName() {
        String existingName = "ExistingCollection";
        defaultCollectionManager.createCollection(existingName, "view", "defaultModel");

        DefaultCollection result = defaultCollectionManager.createCollection(existingName, "edit", "updatedModel");

        assertNull(result, "Should return null for an existing collection name");
        verify(logger).warn("Collection with name {} already exists", existingName);
    }

    @Test
    void testListCollectionsEmpty() {
        List<DefaultCollection> result = defaultCollectionManager.listCollections();
        assertTrue(result.isEmpty(), "Collection list should be empty initially");
    }

    @Test
    void testListCollectionsWithCollections() {
        String name1 = "TestCollection1";
        String permissions1 = "view, edit";
        String embeddingModel1 = "defaultModel1";
        defaultCollectionManager.createCollection(name1, permissions1, embeddingModel1);

        String name2 = "TestCollection2";
        String permissions2 = "view";
        String embeddingModel2 = "defaultModel2";
        defaultCollectionManager.createCollection(name2, permissions2, embeddingModel2);

        List<DefaultCollection> result = defaultCollectionManager.listCollections();

        assertEquals(2, result.size(), "Collection list should contain two collections");
        assertTrue(result.stream().anyMatch(c -> name1.equals(c.getName())), "List should contain TestCollection1");
        assertTrue(result.stream().anyMatch(c -> name2.equals(c.getName())), "List should contain TestCollection2");
    }
}