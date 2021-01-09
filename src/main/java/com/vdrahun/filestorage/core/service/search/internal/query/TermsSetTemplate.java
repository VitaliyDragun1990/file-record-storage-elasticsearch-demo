package com.vdrahun.filestorage.core.service.search.internal.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaliy Dragun
 */
@Getter
@JsonTypeName(value = "terms_set")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class TermsSetTemplate {

    private final Tags tags;

    public static TermsSetTemplate forTags(List<String> tags) {
        return new TermsSetTemplate(new Tags(tags));
    }

    private TermsSetTemplate(Tags tags) {
        this.tags = tags;
    }

    @Getter
    static class Tags {

        private final List<String> terms = new ArrayList<>();

        public Tags(List<String> terms) {
            this.terms.addAll(terms);
        }

        @JsonProperty("minimum_should_match_script")
        private final Tags.MinimumShouldMatchScript minimumShouldMatchScript = new Tags.MinimumShouldMatchScript();

        @Getter
        static class MinimumShouldMatchScript {

            private final String source = "params.num_terms";
        }
    }
}
