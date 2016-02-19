/*
 * Licensed to STRATIO (C) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  The STRATIO (C) licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.stratio.cassandra.lucene.cache;

import org.apache.cassandra.db.Clustering;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.PartitionRangeReadCommand;
import org.apache.cassandra.db.ReadCommand;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

import java.util.UUID;

/**
 * @author Andres de la Pena {@literal <adelapena@stratio.com>}
 */
public class SearchCacheUpdater {

    private final UUID key;
    private final SearchCache cache;
    private final String search;
    private final PartitionRangeReadCommand command;
    private final Query query;

    public SearchCacheUpdater(SearchCache cache,
                              String search,
                              UUID key,
                              ReadCommand command,
                              Query query) {
        this.cache = cache;
        this.search = search;
        this.key = key;
        this.command = command instanceof PartitionRangeReadCommand ? (PartitionRangeReadCommand) command : null;
        this.query = query;
    }

    public void put(DecoratedKey decoratedKey, Clustering clustering, ScoreDoc scoreDoc) {
        if (command != null) {
            cache.put(key, new SearchCacheEntry(cache, search, command, decoratedKey, clustering, scoreDoc, query));
        }
    }

}
