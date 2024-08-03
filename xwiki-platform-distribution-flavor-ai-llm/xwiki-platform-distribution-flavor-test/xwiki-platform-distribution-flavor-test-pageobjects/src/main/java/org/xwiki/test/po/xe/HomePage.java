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
package org.xwiki.test.po.xe;

import org.xwiki.test.ui.po.ViewPage;

/**
 * Represents the actions possible on the Home Page.
 * 
 * @version $Id: 2fd4eaaa0cdac3cdd18a8bb9b2ba5caf7079b94e $
 * @since 3.2M3
 */
public class HomePage extends ViewPage
{
    public static HomePage gotoPage()
    {
        getUtil().gotoPage("Main", "WebHome");
        return new HomePage();
    }

    public String getURL()
    {
        return getUtil().getURL("Main", "WebHome");
    }

    public boolean isOnHomePage()
    {
        return getDriver().getCurrentUrl().equals(getURL());
    }
}
