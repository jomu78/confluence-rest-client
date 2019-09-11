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

import de.muehlencord.confluence.rest.core.api.custom.CqlSearchResultDeserializer;
import de.muehlencord.confluence.rest.core.api.domain.BaseBean;
import de.muehlencord.confluence.rest.core.api.domain.cql.CqlSearchResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * @author Joern Muehlencord (joern@muehlencord.de)
 */
public class AttachmentQueryBean extends BaseBean {

    @Expose
    private String status;

    @Expose
    private String title;

    @Expose
    private VersionBean version;

    @Expose
    private ContainerBean container;

    @Expose
    private MetadataBean metadata;

    @Expose
    private ExtensionsBean extensions;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VersionBean getVersion() {
        return version;
    }

    public void setVersion(VersionBean version) {
        this.version = version;
    }

    public ContainerBean getContainer() {
        return container;
    }

    public void setContainer(ContainerBean container) {
        this.container = container;
    }

    public MetadataBean getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataBean metadata) {
        this.metadata = metadata;
    }

    public ExtensionsBean getExtensions() {
        return extensions;
    }

    public void setExtensions(ExtensionsBean extensions) {
        this.extensions = extensions;
    }
}
