/**
 * Copyright 2019 Joern Muehlencord
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.muehlencord.confluence.rest.core.api.domain.content;

import com.google.gson.annotations.Expose;

/**
 * @author Joern Muehlencord (joern@muehlencord.de)
 */
public class ExtensionsBean {
    
    @Expose
    private String mediaType;
    
    @Expose
    private int fileSize;
    
    @Expose
    private String comment;
    
}
