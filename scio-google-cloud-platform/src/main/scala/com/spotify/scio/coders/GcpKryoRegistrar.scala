/*
 * Copyright 2023 Spotify AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.spotify.scio.coders

import com.google.cloud.bigtable.data.v2.models.MutateRowsException
import com.google.cloud.bigtable.grpc.scanner.BigtableRetriesExhaustedException
import com.spotify.scio.bigquery.TableRow
import com.spotify.scio.coders.instances.kryo.{
  BigtableRetriesExhaustedExceptionSerializer,
  CoderSerializer,
  MutateRowsExceptionSerializer
}
import com.twitter.chill._
import org.apache.beam.sdk.io.gcp.bigquery.TableRowJsonCoder

@KryoRegistrar
class GcpKryoRegistrar extends IKryoRegistrar {
  override def apply(k: Kryo): Unit = {
    k.forClass[TableRow](new CoderSerializer(TableRowJsonCoder.of()))
    k.forClass[BigtableRetriesExhaustedException](new BigtableRetriesExhaustedExceptionSerializer)
    k.forClass[MutateRowsException](new MutateRowsExceptionSerializer)
  }
}
