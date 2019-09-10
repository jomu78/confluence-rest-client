/**
 * Copyright 2016 Micromata GmbH
 * Modifications Copyright 2017 Martin Böhmer
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
package com.github.jomu78.confluence.rest.client.impl;

import static com.github.jomu78.confluence.rest.core.api.misc.RestParamConstants.EXPAND;
import static com.github.jomu78.confluence.rest.core.api.misc.RestParamConstants.LIMIT;
import static com.github.jomu78.confluence.rest.core.api.misc.RestParamConstants.POSTING_DAY;
import static com.github.jomu78.confluence.rest.core.api.misc.RestParamConstants.SPACEKEY;
import static com.github.jomu78.confluence.rest.core.api.misc.RestParamConstants.START;
import static com.github.jomu78.confluence.rest.core.api.misc.RestParamConstants.STATUS;
import static com.github.jomu78.confluence.rest.core.api.misc.RestParamConstants.TITLE;
import static com.github.jomu78.confluence.rest.core.api.misc.RestParamConstants.TYPE;
import static com.github.jomu78.confluence.rest.core.api.misc.RestParamConstants.VERSION;
import static com.github.jomu78.confluence.rest.core.api.misc.RestPathConstants.CONTENT;
import static com.github.jomu78.confluence.rest.core.api.misc.RestPathConstants.CONTENT_ATTACHMENT;
import static com.github.jomu78.confluence.rest.core.api.misc.RestPathConstants.CONTENT_LABEL;
import static com.github.jomu78.confluence.rest.core.api.misc.RestPathConstants.SPECIFIC_CONTENT;
import static com.github.jomu78.confluence.rest.core.api.misc.RestPathConstants.CONVERT_TO_STORAGE_CONTENT;

import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jomu78.confluence.rest.client.api.ContentClient;
import com.github.jomu78.confluence.rest.core.api.RequestService;
import com.github.jomu78.confluence.rest.core.api.domain.content.AttachmentBean;
import com.github.jomu78.confluence.rest.core.api.domain.content.AttachmentQueryBean;
import com.github.jomu78.confluence.rest.core.api.domain.content.AttachmentResultsBean;
import com.github.jomu78.confluence.rest.core.api.domain.content.CommentBean;
import com.github.jomu78.confluence.rest.core.api.domain.content.CommentResultsBean;
import com.github.jomu78.confluence.rest.core.api.domain.content.ContentBean;
import com.github.jomu78.confluence.rest.core.api.domain.content.ContentResultsBean;
import com.github.jomu78.confluence.rest.core.api.domain.content.LabelBean;
import com.github.jomu78.confluence.rest.core.api.domain.content.LabelsBean;
import com.github.jomu78.confluence.rest.core.api.domain.content.StorageBean;
import com.github.jomu78.confluence.rest.core.impl.APIUriProvider;
import com.github.jomu78.confluence.rest.core.api.misc.ContentStatus;
import com.github.jomu78.confluence.rest.core.api.misc.ContentType;
import com.github.jomu78.confluence.rest.core.api.misc.RestPathConstants;
import com.github.jomu78.confluence.rest.core.api.misc.UnexpectedContentException;

/**
 * @author Christian Schulze (c.schulze@micromata.de)
 * @author Martin Böhmer
 * @author Joern Muehlencord (joern@muehlencord.de)
 */
public class ContentClientImpl extends BaseClientImpl implements ContentClient {

    private final Logger log = LoggerFactory.getLogger(ContentClientImpl.class);

    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ContentClientImpl(ExecutorService executorService, RequestService requestService, APIUriProvider apiConfig) {
        super(executorService, requestService, apiConfig);
    }

    @Override
    public Future<ContentBean> getContentById(String id, int version, List<String> expand) {
        if (log.isInfoEnabled()) {
            String message = "Getting content by ID. ID=%1$s, version=%2$s, expand=%3$s";
            log.info(String.format(message, id, version, expand));
        }
        return executorService.submit(() -> {
            // URI with parameters
            URIBuilder uriBuilder = buildPath(CONTENT, id);
            if (version > 0) {
                uriBuilder.addParameter(VERSION, String.valueOf(version));
            }
            if (CollectionUtils.isNotEmpty(expand) == true) {
                String join = StringUtils.join(expand, ",");
                uriBuilder.addParameter(EXPAND, join);
            }
            return executeGetRequest(uriBuilder.build(), ContentBean.class);
        });
    }

    @Override
    public Future<ContentResultsBean> getContent(ContentType type, String spacekey, String title, ContentStatus status, Date postingDay, List<String> expand, int start, int limit) {
        if (log.isInfoEnabled()) {
            String message = "Getting content. Type=%1$s, space=%2$s, title=%3$s, status=%4$s, postingDay=%5$s, expand=%6$s, start=%7$s, limit=%8$s";
            log.info(String.format(message, type, spacekey, title, status, postingDay, expand, start, limit));
        }
        // URI with parameters
        URIBuilder uriBuilder = buildPath(CONTENT);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (type != null) {
            nameValuePairs.add(new BasicNameValuePair(TYPE, type.getName()));
        }
        if (StringUtils.trimToNull(spacekey) != null) {
            nameValuePairs.add(new BasicNameValuePair(SPACEKEY, spacekey));
        }
        if (StringUtils.trimToNull(title) != null) {
            nameValuePairs.add(new BasicNameValuePair(TITLE, title));
        }
        if (status != null) {
            nameValuePairs.add(new BasicNameValuePair(STATUS, status.getName()));
        }
        if (postingDay != null) {
            nameValuePairs.add(new BasicNameValuePair(POSTING_DAY, sdf.format(postingDay)));
        }
        if (expand != null && expand.isEmpty() == false) {
            String join = StringUtils.join(expand, ",");
            nameValuePairs.add(new BasicNameValuePair(EXPAND, join));
        }
        if (start > 0) {
            nameValuePairs.add(new BasicNameValuePair(START, String.valueOf(start)));
        }
        if (limit > 0) {
            nameValuePairs.add(new BasicNameValuePair(LIMIT, String.valueOf(limit)));
        }
        uriBuilder.addParameters(nameValuePairs);
        // Request
        return executorService.submit(() -> {
            return executeGetRequest(uriBuilder.build(), ContentResultsBean.class);
        });
    }

    @Override
    public Future<CommentResultsBean> getComment(String spacekey, String title, ContentStatus status, Date postingDay, List<String> expand, int start, int limit) {
        // first we search for the parent content to get its id

        if (log.isInfoEnabled()) {
            String message = "Getting comment. Space=%1$s, pageTitle=%2$s, status=%3$s, postingDay=%4$s, expand=%5$s, start=%6$s, limit=%7$s";
            log.info(String.format(message, spacekey, title, status, postingDay, expand, start, limit));
        }

        // Request
        return executorService.submit(() -> {
            if (log.isInfoEnabled()) {
                String message = "Getting content. Type=%1$s, space=%2$s, title=%3$s, status=%4$s, postingDay=%5$s, expand=%6$s, start=%7$s, limit=%8$s";
                log.info(String.format(message, ContentType.PAGE, spacekey, title, status, postingDay, expand, start, limit));
            }
            // URI with parameters
            URIBuilder pageUriBuilder = buildPath(CONTENT);
            List<NameValuePair> pageNameValuePairs = new ArrayList<>();
            pageNameValuePairs.add(new BasicNameValuePair(TYPE, ContentType.PAGE.getName()));
            if (StringUtils.trimToNull(spacekey) != null) {
                pageNameValuePairs.add(new BasicNameValuePair(SPACEKEY, spacekey));
            }
            if (StringUtils.trimToNull(title) != null) {
                pageNameValuePairs.add(new BasicNameValuePair(TITLE, title));
            }
            if (status != null) {
                pageNameValuePairs.add(new BasicNameValuePair(STATUS, status.getName()));
            }
            if (postingDay != null) {
                pageNameValuePairs.add(new BasicNameValuePair(POSTING_DAY, sdf.format(postingDay)));
            }
            if (expand != null && expand.isEmpty() == false) {
                String join = StringUtils.join(expand, ",");
                pageNameValuePairs.add(new BasicNameValuePair(EXPAND, join));
            }
            if (start > 0) {
                pageNameValuePairs.add(new BasicNameValuePair(START, String.valueOf(start)));
            }
            if (limit > 0) {
                pageNameValuePairs.add(new BasicNameValuePair(LIMIT, String.valueOf(limit)));
            }
            pageUriBuilder.addParameters(pageNameValuePairs);
            ContentResultsBean executeGetRequest = executeGetRequest(pageUriBuilder.build(), ContentResultsBean.class);
            String pageId = executeGetRequest.getResults().get(0).getId();
            // URI with parameters
            URIBuilder uriBuilder = buildPath(String.format(RestPathConstants.CONTENT_COMMENT, pageId));
            List<NameValuePair> nameValuePairs = new ArrayList<>();

            if (StringUtils.trimToNull(spacekey) != null) {
                nameValuePairs.add(new BasicNameValuePair(SPACEKEY, spacekey));
            }
            if (StringUtils.trimToNull(title) != null) {
                nameValuePairs.add(new BasicNameValuePair(TITLE, title));
            }
            if (status != null) {
                nameValuePairs.add(new BasicNameValuePair(STATUS, status.getName()));
            }
            if (postingDay != null) {
                nameValuePairs.add(new BasicNameValuePair(POSTING_DAY, sdf.format(postingDay)));
            }
            if (expand != null && expand.isEmpty() == false) {
                String join = StringUtils.join(expand, ",");
                nameValuePairs.add(new BasicNameValuePair(EXPAND, join));
            }
            if (start > 0) {
                nameValuePairs.add(new BasicNameValuePair(START, String.valueOf(start)));
            }
            if (limit > 0) {
                nameValuePairs.add(new BasicNameValuePair(LIMIT, String.valueOf(limit)));
            }
            uriBuilder.addParameters(nameValuePairs);
            return executeGetRequest(uriBuilder.build(), CommentResultsBean.class);
        });
    }

    @Override
    public Future<CommentBean> createComment(CommentBean comment) {
        if (log.isInfoEnabled()) {
            String message = "Creating comment. Value=%1$s, representation=%2$s";
            log.info(String.format(message, comment.getBody().getStorage().getValue(), comment.getBody().getStorage().getRepresentation()));
        }
        // Request
        return executorService.submit(() -> {
            URI uri = buildPath(CONTENT).build();
            return executePostRequest(uri, comment, CommentBean.class);
        });
    }

    @Override
    public Future<CommentBean> updateComment(CommentBean comment) {
        if (log.isInfoEnabled()) {
            String message = "Updating comment. Value=%1$s, representation=%2$s";
            log.info(String.format(message, comment.getBody().getStorage().getValue(), comment.getBody().getStorage().getRepresentation()));
        }
        // Request
        return executorService.submit(() -> {
            String contentUriPath = String.format(SPECIFIC_CONTENT, comment.getId());
            URI uri = buildPath(contentUriPath).build();
            return executePutRequest(uri, comment, CommentBean.class);
        });
    }

    @Override
    public Future<ContentBean> createContent(ContentBean content) {
        if (log.isInfoEnabled()) {
            String message = "Creating content. Title=%1$s, space=%2$s";
            String spaceKey = (content.getSpace() != null) ? content.getSpace().getKey() : null;
            log.info(String.format(message, content.getTitle(), spaceKey));
        }
        // Request
        return executorService.submit(() -> {
            URI uri = buildPath(CONTENT).build();
            return executePostRequest(uri, content, ContentBean.class);
        });
    }

    @Override
    public Future<ContentBean> updateContent(ContentBean content) {
        if (log.isInfoEnabled()) {
            String message = "Updating content. Title=%1$s, space=%2$s";
            String spaceKey = (content.getSpace() != null) ? content.getSpace().getKey() : null;
            log.info(String.format(message, content.getTitle(), spaceKey));
        }
        // Request
        return executorService.submit(() -> {
            String contentUriPath = String.format(SPECIFIC_CONTENT, content.getId());
            URI uri = buildPath(contentUriPath).build();
            return executePutRequest(uri, content, ContentBean.class);
        });
    }

    @Override
    public Future<AttachmentBean> uploadAttachment(AttachmentBean attachment, ContentBean parentContent)  {
        return executorService.submit(() -> {
            // URI
            // if a new file should be uploaded: /content/{id}/child/attachment
            // if an existing file should be replaced: /content/{id}/child/attachment/{attachmentId}/data
            String attachmentUriPath = String.format(CONTENT_ATTACHMENT, parentContent.getId());
            URI uri = buildPath(attachmentUriPath).build();
            // Check for comment
            String comment = null;
            if (attachment.getMetadata() != null && attachment.getMetadata().getComment() != null) {
                comment = attachment.getMetadata().getComment();
            }

            // Request
            AttachmentResultsBean results = executePostRequestForUpload(uri, attachment.getInputStream(), attachment.getTitle(), comment, AttachmentResultsBean.class);

            // Extract result
            int numberOfResults = 0;
            if (results.getResults() != null) {
                numberOfResults = results.getResults().size();
            }
            if (numberOfResults == 1) {
                return (AttachmentBean) results.getResults().get(0);
            } else {
                throw new UnexpectedContentException("Attachment result set with 1 element", "Attachemnt result set with " + numberOfResults + " elements");
            }
        });
    }

    @Override
    public Future<AttachmentQueryBean> updateAttachment(AttachmentBean attachment, ContentBean parentContent) {
        return executorService.submit(() -> {

            if (attachment.getId() == null) {
                throw new IllegalArgumentException("ID of the attachment cannot be null");
            }

            // URI
            // if a new file should be uploaded: /content/{id}/child/attachment
            // if an existing file should be replaced: /content/{id}/child/attachment/{attachmentId}/data
            String attachmentUriPath = String.format(RestPathConstants.CONTENT_EXISTING_ATTACHMENT, parentContent.getId(), attachment.getId());
            URI uri = buildPath(attachmentUriPath).build();

            // Check for comment
            String comment = null;
            if (attachment.getMetadata() != null && attachment.getMetadata().getComment() != null) {
                comment = attachment.getMetadata().getComment();
            }

            // Request
            AttachmentQueryBean results = executePostRequestForUpdateUpload(uri, attachment.getInputStream(), attachment.getTitle(), comment, AttachmentQueryBean.class);
            if (results == null) {
                throw new UnexpectedContentException("Attachment result", "null value");
            } else {
                return results;
            }
        });
    }

    @Override
    public Future<InputStream> downloadAttachement(AttachmentBean attachment) {
        // Check input
        if (attachment.getId() == null) {
            throw new IllegalArgumentException("ID of the attachment cannot be null");
        }
        if (attachment.getTitle() == null) {
            throw new IllegalArgumentException("Title of the attachment cannot be null");
        }
        return executorService.submit(() -> {
            // Determine download URI
            String downloadUriPath;
            if (attachment.getLinks() != null) {
                // Provided
                downloadUriPath = attachment.getLinks().getDownload();
            } else {
                // Not Provided
                Future<ContentBean> future = getContentById(attachment.getId(), 0, null);
                ContentBean attachmentContent = future.get();
                downloadUriPath = attachmentContent.getLinks().getDownload();
            }
            URI uri = buildNonRestPath(downloadUriPath).build();
            // Request
            return executeGetRequestForDownload(uri);
        });
    }

    @Override
    public Future<LabelsBean> addLabels(ContentBean content, List<LabelBean> labels) {
        if (log.isInfoEnabled()) {
            String message = "Adding labels to content. Content ID=%1$s, labels %2$s";
            log.info(String.format(message, content.getId(), labels));
        }

        return executorService.submit(() -> {
            // URI
            String attachmentUriPath = String.format(CONTENT_LABEL, content.getId());
            URI uri = buildPath(attachmentUriPath).build();
            // Request
            return executePostRequest(uri, content, LabelsBean.class);
        });
    }

    @Override
    public Future<StorageBean> convertContent(StorageBean storage) {
        if (log.isInfoEnabled()) {
            String message = "Converting content to storage representation";
            log.info(message);
        }

        // Request
        return executorService.submit(() -> {
            URI uri = buildPath(CONVERT_TO_STORAGE_CONTENT).build();
            return executePostRequest(uri, storage, StorageBean.class);
        });
    }

}
