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
import org.xwiki.context.Execution;
import org.xwiki.contrib.llm.Document;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
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
    private static final String PARENT_COLLECTION = "collection";
    private static final String MIMETYPE_KEY = "mimetype";
    private static final String CONTENT_KEY = "content";
    private static final String EMBEDDINGS_KEY = "embeddings";
    
    /**
     * The execution, to get the context from it.
     */
    @Inject
    protected Execution execution;
    
    private String id;
    private String title;
    private String language;
    private String url;
    private String collection;
    private String mimetype;
    private String content;
    private String embeddings;
    
    private XWikiDocument xdocument;
    private BaseObject xobject;
    
    
    /**
     * Handles the initialization of the component.
     * 
     * @param xdocument
     */
    public void initialize(XWikiDocument xdocument)
    {
        XWikiContext context = getXContext();
        this.xdocument = xdocument;
        try {
            this.xobject = xdocument.newXObject(Document.STORAGE_XWIKI_DOCUMENT_CLASS, context);
        } catch (XWikiException e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public Map<String, String> getProperties()
    {
        Map<String, String> properties = new HashMap<>();
        properties.put(ID_KEY, id);
        properties.put(TITLE_KEY, title);
        properties.put(LANG_KEY, language);
        properties.put(URL_KEY, url);
        properties.put(PARENT_COLLECTION, collection);
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
            case PARENT_COLLECTION:
                property = Optional.ofNullable(collection);
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
        return xobject.getStringValue(LANG_KEY);
    }

    @Override
    public String getURL()
    {
        return url;
    }

    @Override
    public String getCollection()
    {
        return collection;
    }

    @Override
    public String getMimetype()
    {
        return mimetype;
    }

    @Override
    public String getContent()
    {
        return this.xdocument.getContent();
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
        this.xobject.setStringValue(LANG_KEY, language);
    }

    @Override
    public void setURL(String url)
    {
        this.url = url;
    }
    
    @Override
    public void setCollection(String collection)
    {
        this.collection = collection;
    }

    @Override
    public void setMimetype(String mimetype)
    {
        this.mimetype = mimetype;
    }

    @Override
    public void setContent(String content)
    {
        this.xdocument.setContent(content);
    }

    @Override
    public void setEmbeddings(String embeddings)
    {
        this.embeddings = embeddings;
    }

    @Override
    public void save() throws XWikiException
    {
        XWikiContext context = getXContext();
        context.getWiki().saveDocument(this.xdocument, "Saving document", context);
    }
    
    /**
     * @return the xwiki context from the execution context
     */
    protected XWikiContext getXContext()
    {
        return (XWikiContext) execution.getContext().getProperty("xwikicontext");
    }
}
