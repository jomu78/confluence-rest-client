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
package com.github.jomu78.confluence.rest.core.api.domain.content;

import com.github.jomu78.confluence.rest.core.api.custom.CqlSearchResultDeserializer;
import com.github.jomu78.confluence.rest.core.api.domain.BaseBean;
import com.github.jomu78.confluence.rest.core.api.domain.cql.CqlSearchResult;
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

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(CqlSearchResult.class, new CqlSearchResultDeserializer()).create();
        String json = "{\n"
                + "            \"id\": \"176503419\",\n"
                + "            \"type\": \"attachment\",\n"
                + "            \"status\": \"current\",\n"
                + "            \"title\": \"proflex500.properties\",\n"
                + "            \"metadata\": {\n"
                + "                \"comment\": \"control file\",\n"
                + "                \"mediaType\": \"application/octet-stream\"\n"
                + "            },\n"
                + "            \"extensions\": {\n"
                + "                \"mediaType\": \"application/octet-stream\",\n"
                + "                \"fileSize\": 1994,\n"
                + "                \"comment\": \"control file\"\n"
                + "            },\n"
                + "            \"_links\": {\n"
                + "                \"webui\": \"/display/DSSTS/Version+5.0.0+PF500?preview=%2F147510138%2F176503419%2Fproflex500.properties\",\n"
                + "                \"download\": \"/download/attachments/147510138/proflex500.properties?version=1&modificationDate=1568036386758&api=v2\",\n"
                + "                \"self\": \"https://spike.wincor-nixdorf.com/rest/api/content/176503419\"\n"
                + "            },\n"
                + "            \"_expandable\": {\n"
                + "                \"container\": \"/rest/api/content/147510138\",\n"
                + "                \"operations\": \"\",\n"
                + "                \"children\": \"/rest/api/content/176503419/child\",\n"
                + "                \"restrictions\": \"/rest/api/content/176503419/restriction/byOperation\",\n"
                + "                \"history\": \"/rest/api/content/176503419/history\",\n"
                + "                \"ancestors\": \"\",\n"
                + "                \"body\": \"\",\n"
                + "                \"version\": \"\",\n"
                + "                \"descendants\": \"/rest/api/content/176503419/descendant\",\n"
                + "                \"space\": \"/rest/api/space/DSSTS\"\n"
                + "            }\n"
                + "        }";
        DescendantsBean bean = gson.fromJson(json, DescendantsBean.class);
    }

}
