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
package org.xwiki.test.storage;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.xwiki.component.phase.Initializable;
import org.xwiki.test.integration.XWikiExecutor;
import org.xwiki.test.storage.framework.AbstractTest;
import org.xwiki.test.storage.profiles.ForEachProfileSuite;

/**
 * Runs all functional tests found in the classpath.
 * 
 * @version $Id: 8435b563221c83cd23ad093d978ab6a46166bac8 $
 * @since 3.0RC1
 */
@RunWith(ForEachProfileSuite.class)
public class AllTests implements Initializable
{
    @Inject
    private XWikiExecutor executor;

    @Override
    public void initialize()
    {
        AbstractTest.setExecutor(executor);
    }
}
