/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.skywalking.oap.server.receiver.event.listener;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.skywalking.apm.network.event.v3.Event;
import org.apache.skywalking.apm.network.event.v3.Source;
import org.apache.skywalking.oap.server.core.CoreModule;
import org.apache.skywalking.oap.server.core.analysis.worker.RecordStreamProcessor;
import org.apache.skywalking.oap.server.core.config.NamingControl;
import org.apache.skywalking.oap.server.core.event.EventRecord;
import org.apache.skywalking.oap.server.library.module.ModuleManager;

/**
 * RecordAnalysisListener forwards the event data to the persistence layer with the query required conditions.
 */
@RequiredArgsConstructor
public class RecordAnalysisListener implements EventAnalysisListener {
    private static final Gson GSON = new Gson();

    private final NamingControl namingControl;

    private final EventRecord record = new EventRecord();

    @Override
    public void build() {
        RecordStreamProcessor.getInstance().in(record);
    }

    @Override
    public void parse(final Event event) {
        record.setUuid(event.getUuid());

        if (event.hasSource()) {
            final Source source = event.getSource();
            record.setService(namingControl.formatServiceName(source.getService()));
            record.setServiceInstance(namingControl.formatInstanceName(source.getServiceInstance()));
            record.setEndpoint(namingControl.formatEndpointName(source.getService(), source.getEndpoint()));
        }

        record.setName(event.getName());
        record.setType(event.getType().name());
        record.setMessage(event.getMessage());
        record.setParameters(GSON.toJson(event.getParametersMap()));
        record.setStartTime(event.getStartTime());
        record.setEndTime(event.getEndTime());
    }

    public static class Factory implements EventAnalysisListener.Factory {
        private final NamingControl namingControl;

        public Factory(final ModuleManager moduleManager) {
            this.namingControl = moduleManager.find(CoreModule.NAME)
                                              .provider()
                                              .getService(NamingControl.class);
        }

        @Override
        public EventAnalysisListener create(final ModuleManager moduleManager) {
            return new RecordAnalysisListener(namingControl);
        }
    }
}
