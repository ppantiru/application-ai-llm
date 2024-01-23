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
package org.xwiki.contrib.llm.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.xwiki.contrib.llm.ChatModelDescriptor;
import org.xwiki.rest.XWikiRestException;
import org.xwiki.stability.Unstable;

import com.theokanning.openai.OpenAiResponse;

/**
 * REST resource for listing models.
 *
 * @version $Id$
 * @since 0.3
 */
@Unstable
@Path("/wikis/{wikiName}/aiLLM/v1/models")
public interface ModelsResource
{
    /**
     * Gets a list of all available (chat) models.
     *
     * @param wikiName the wiki in which the models are located
     * @return the list of models
     * @throws XWikiRestException when there is an error retrieving the models
     */
    @GET
    OpenAiResponse<ChatModelDescriptor> getModels(@PathParam("wikiName") String wikiName) throws XWikiRestException;
}
