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

import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiAttachment;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.IOException;
import java.util.List;
/**
 * Implementation of a {@code Document} component.
 *
 * @version $Id$
 */
@Component(roles = DefaultDocument.class)
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
public class DefaultDocument implements Document
{
    private XWikiDocument document;
    private XWikiContext context;

    /**
     * Handles the initialization of the component.
     * 
     * @param document
     * @param context
     */
    public void initialize(XWikiDocument document, XWikiContext context)
    {
        this.document = document;
        this.context = context;
    }

    @Override
    public String getID()
    {
        return String.valueOf(this.document.getDocumentReference());
    }

    @Override
    public String getTitle()
    {
        return this.document.getTitle();
    }

    @Override
    public String getLanguage()
    {
        return this.document.getLocale().getLanguage();
    }

    @Override
    public String getURL()
    {
        return this.document.getURL("view", context);
    }

    @Override
    public String getMimeType()
    {
        List<XWikiAttachment> attachmentList = this.document.getAttachmentList();
    
        // Return the MIME type of the first attachment if available, else a default message.
        return attachmentList.isEmpty() 
               ? "No attachment found!" 
               : attachmentList.get(0).getMimeType();
    }
    

    @Override
    public String getContent() throws IOException, TikaException, XWikiException
    {
        // Check if the document has direct content
        String content = this.document.getContent();
        if (content != null) {
            return content;
        }
    
        // Process the first attachment if available
        List<XWikiAttachment> attachmentList = this.document.getAttachmentList();
        if (!attachmentList.isEmpty()) {
            XWikiAttachment attachment = attachmentList.get(0);
            Tika tika = new Tika();
            return tika.parseToString(attachment.getContentInputStream(context));
        }
    
        // Return a default message if there's no content or attachments
        return "No content found!";
    }
    

}

