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

import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.contrib.llm.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
/**
 * Implementation of a {@code Document} component.
 *
 * @version $Id$
 */
@Component(roles = DefaultDocument.class)
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
/**
 * DefaultDocument implements the Document interface to provide access to an XWikiDocument and related data. It allows
 * retrieving metadata like the document id, title, language, URL, MIME type, and content. The class handles
 * initializing itself with an XWikiDocument and XWikiContext. It provides implementations of Document methods to expose
 * document properties and content.
 */
public class DefaultDocument implements Document
{
    private static final String ID_KEY = "id";
    private static final String TITLE_KEY = "title";
    private static final String LANG_KEY = "language";
    private static final String URL_KEY = "url";
    private static final String MIMETYPE_KEY = "mimetype";
    private static final String CONTENT_KEY = "content";
    private static final String EMBEDDINGS_KEY = "embeddings";

    private String id;
    private String title;
    private String language;
    private String url;
    private String mimetype;
    private String content;
    private String embeddings;

    @Override
    public Map<String, String> getProperties()
    {
        Map<String, String> properties = new HashMap<>();
        properties.put(ID_KEY, id);
        properties.put(TITLE_KEY, title);
        properties.put(LANG_KEY, language);
        properties.put(URL_KEY, url);
        properties.put(MIMETYPE_KEY, mimetype);
        properties.put(CONTENT_KEY, content);
        properties.put(EMBEDDINGS_KEY, embeddings);
        return properties;
    }

    /**
     * Gets the property value based on the key.
     *
     * @param key the property key
     * @return an Optional containing the property value if present
     */
    public Optional<String> getProperty(String key)
    {
        Optional<String> property;
        switch (key) {
            case ID_KEY:
                property = Optional.ofNullable(id);
                break;
            case TITLE_KEY:
                property = Optional.ofNullable(title);
                break;
            case LANG_KEY:
                property = Optional.ofNullable(language);
                break;
            case URL_KEY:
                property = Optional.ofNullable(url);
                break;
            case MIMETYPE_KEY:
                property = Optional.ofNullable(mimetype);
                break;
            case CONTENT_KEY:
                property = Optional.ofNullable(content);
                break;
            case EMBEDDINGS_KEY:
                property = Optional.ofNullable(embeddings);
                break;
            default:
                property = Optional.empty();
                break;
        }
        return property;
    }
    @Override
    public String getId()
    {
        return id;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public String getLanguage()
    {
        return language;
    }

    @Override
    public String getURL()
    {
        return url;
    }

    @Override
    public String getMimetype()
    {
        return mimetype;
    }

    @Override
    public String getContent()
    {
        return content;
    }

    @Override
    public String getEmbeddings()
    {
        return embeddings;
    }

    @Override
    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public void setLanguage(String language)
    {
        this.language = language;
    }

    @Override
    public void setURL(String url)
    {
        this.url = url;
    }

    @Override
    public void setMimetype(String mimetype)
    {
        this.mimetype = mimetype;
    }

    @Override
    public void setContent(String content)
    {
        this.content = content;
    }

    @Override
    public void setEmbeddings(String embeddings)
    {
        this.embeddings = embeddings;
    }
}
