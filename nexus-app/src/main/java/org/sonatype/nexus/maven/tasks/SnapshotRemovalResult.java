/**
 * ﻿Sonatype Nexus (TM) [Open Source Version].
 * Copyright (c) 2008 Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at ${thirdpartyurl}.
 *
 * This program is licensed to you under Version 3 only of the GNU General
 * Public License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * Version 3 for more details.
 *
 * You should have received a copy of the GNU General Public License
 * Version 3 along with this program. If not, see http://www.gnu.org/licenses/.
 */
package org.sonatype.nexus.maven.tasks;

import java.util.HashMap;
import java.util.Map;

public class SnapshotRemovalResult
{
    private Map<String, SnapshotRemovalRepositoryResult> processedRepositories;

    public SnapshotRemovalResult()
    {
        super();

        this.processedRepositories = new HashMap<String, SnapshotRemovalRepositoryResult>();
    }

    public Map<String, SnapshotRemovalRepositoryResult> getProcessedRepositories()
    {
        return processedRepositories;
    }

    public void addResult( SnapshotRemovalRepositoryResult res )
    {
        if ( processedRepositories.containsKey( res.getRepositoryId() ) )
        {
            SnapshotRemovalRepositoryResult ex = processedRepositories.get( res.getRepositoryId() );

            ex.setDeletedFiles( ex.getDeletedFiles() + res.getDeletedFiles() );

            ex.setDeletedSnapshots( ex.getDeletedSnapshots() + res.getDeletedSnapshots() );
        }
        else
        {
            processedRepositories.put( res.getRepositoryId(), res );
        }
    }

}
