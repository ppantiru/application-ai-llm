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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.xwiki.contrib.llm.internal.DefaultDocument;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.doc.XWikiDocument;
import org.xwiki.context.ExecutionContext;

public class DefaultDocumentTest
{

    private DefaultDocument defaultDocument;
    private XWikiDocument document;
    private XWikiContext context;

    @BeforeEach
    public void setUp() {
        // Mock the XWikiDocument and XWikiContext classes
        document = Mockito.mock(XWikiDocument.class);
        context = Mockito.mock(XWikiContext.class);
        
        // Create a new DefaultDocument instance and initialize it
        defaultDocument = new DefaultDocument();
        defaultDocument.initialize(document, context);
    }

    @Test
    public void getTitleTest() {
        String expectedTitle = "Test Title";
        when(document.getTitle()).thenReturn(expectedTitle);

        String actualTitle = defaultDocument.getTitle();

        assertEquals(expectedTitle, actualTitle, "The title returned by getTitle() did not match the expected value");
    }

}
