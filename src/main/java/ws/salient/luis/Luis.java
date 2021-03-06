/*
 * Copyright 2016 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ws.salient.luis;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.kie.api.runtime.KieSession;
import ws.salient.chat.Message;


public class Luis {
    
    private Properties properties;
    private KieSession session;
    
    @Inject
    public Luis(Properties properties, KieSession session) {
        this.properties = properties;
        this.session = session;
    }
    
    public void getIntent(Message message) {
        Map parameters = new LinkedHashMap();
        parameters.put("message", message);
        parameters.put("properties", properties.entrySet().stream().filter((entry) -> {
            return entry.getKey().toString().startsWith("ws.salient.luis.");
        }).collect(Collectors.toMap((entry) -> {
            return entry.getKey().toString().substring("ws.salient.luis.".length());
        }, (entry) -> {
            return entry.getValue();
        })));
        session.startProcess("ws.salient.luis.GetIntent", parameters);
    }
}
