/**
 * Sonatype Nexus (TM) Open Source Version.
 * Copyright (c) 2008 Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://nexus.sonatype.org/dev/attributions.html
 * This program is licensed to you under Version 3 only of the GNU General Public License as published by the Free Software Foundation.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License Version 3 for more details.
 * You should have received a copy of the GNU General Public License Version 3 along with this program.
 * If not, see http://www.gnu.org/licenses/.
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc.
 * "Sonatype" and "Sonatype Nexus" are trademarks of Sonatype, Inc.
 */
package org.sonatype.nexus.integrationtests.webproxy.nexus1113;

import static org.sonatype.nexus.integrationtests.ITGroups.PROXY;

import java.io.File;

import org.sonatype.nexus.integrationtests.webproxy.AbstractNexusWebProxyIntegrationTest;
import org.sonatype.nexus.test.utils.FileTestingUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Nexus1113WebProxyWithAuthenticationIT
    extends AbstractNexusWebProxyIntegrationTest
{

    @Override
    @BeforeMethod(alwaysRun = true)
    public void startWebProxy()
        throws Exception
    {
        super.startWebProxy();
        server.getProxyServlet().setUseAuthentication( true );
        server.getProxyServlet().getAuthentications().put( "admin", "123" );
    }

    @Test(groups = PROXY)
    public void downloadArtifactOverWebProxy()
        throws Exception
    {
        File pomFile = this.getLocalFile( "release-proxy-repo-1", "nexus1113", "artifact", "1.0", "pom" );
        File pomArtifact =
            this.downloadArtifact( "nexus1113", "artifact", "1.0", "pom", null, "target/downloads/nexus1113" );
        Assert.assertTrue( FileTestingUtils.compareFileSHA1s( pomArtifact, pomFile ) );

        File jarFile = this.getLocalFile( "release-proxy-repo-1", "nexus1113", "artifact", "1.0", "jar" );
        File jarArtifact =
            this.downloadArtifact( "nexus1113", "artifact", "1.0", "jar", null, "target/downloads/nexus1113" );
        Assert.assertTrue( FileTestingUtils.compareFileSHA1s( jarArtifact, jarFile ) );

        String artifactUrl = baseProxyURL + "release-proxy-repo-1/nexus1113/artifact/1.0/artifact-1.0.jar";
        Assert.assertTrue( server.getAccessedUris().contains( artifactUrl ), "Proxy was not accessed" );
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void stopWebProxy()
        throws Exception
    {
        if ( server != null )
        {
            if ( server.getProxyServlet() != null )
            {
                server.getProxyServlet().setUseAuthentication( false );
                server.getProxyServlet().setAuthentications( null );
            }
        }

        super.stopWebProxy();
    }
}