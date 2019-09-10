/**
 * Copyright 2016 Micromata GmbH
 * Modifications Copyright 2019 Joern Muehlencord
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

import com.github.jomu78.confluence.rest.core.api.domain.BaseBean;
import com.google.gson.annotations.Expose;

/**
 * @author Christian Schulze (c.schulze@micromata.de)
 * @author Joern Muehlencord (joern@muehlencord.de)
 */
public class DescendantsBean extends BaseBean {

    @Expose
    AttachmentQueryResultsBean attachment = null;

    @Expose
    CommentResultsBean comment = null;

    public AttachmentQueryResultsBean getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentQueryResultsBean attachment) {
        this.attachment = attachment;
    }

    public CommentResultsBean getComment() {
        return comment;
    }

    public void setComment(CommentResultsBean comment) {
        this.comment = comment;
    }

}
