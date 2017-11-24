package com.futuresimple.triggear

class Request {
    TriggeringEvent registrationEvent
    List<String> labels = []
    List<PipelineParameters> requestedParameters = []
    List<String> changeRestrictions = []
    List<String> branchRestrictions = []

    private Request(TriggeringEvent type){
        registrationEvent = type
    }

    private void addLabel(String label){
        labels.add(label)
    }

    private void addChangeRestriction(String pathPrefix){
        changeRestrictions.add(pathPrefix)
    }

    private void addBranchRestriction(String branch){
        branchRestrictions.add(branch)
    }

    private void addBranchAsParameter(){
        requestedParameters.add(PipelineParameters.BRANCH)
    }

    private void addShaAsParameter(){
        requestedParameters.add(PipelineParameters.SHA)
    }

    private void addTagAsParameter(){
        requestedParameters.add(PipelineParameters.TAG)
    }

    static PushBuilder forPushes(){
        return new PushBuilder()
    }

    static TagBuilder forTags(){
        return new TagBuilder()
    }

    static LabelBuilder forLabels(String... labels){
        return new LabelBuilder(labels)
    }

    static PrBuilder forPrOpened(){
        return new PrBuilder()
    }

    static class PushBuilder extends Builder {
        PushBuilder(){
            super(TriggeringEvent.PUSH)
        }

        PushBuilder addBranchRestriction(String branch){
            request.addBranchRestriction(branch)
            return this
        }

        PushBuilder addChangeRestriction(String pathPrefix){
            request.addChangeRestriction(pathPrefix)
            return this
        }
    }

    static class TagBuilder extends Builder {
        TagBuilder(){
            super(TriggeringEvent.TAG)
        }

        TagBuilder addTagAsParameter(){
            request.addTagAsParameter()
            return this
        }

        TagBuilder addBranchRestriction(String branch){
            request.addBranchRestriction(branch)
            return this
        }
    }

    static class LabelBuilder extends Builder {
        LabelBuilder(String... labels){
            super(TriggeringEvent.LABEL)
            request.labels = labels
        }

        LabelBuilder addLabel(String label){
            request.addLabel(label)
            return this
        }
    }

    static class PrBuilder extends Builder {
        PrBuilder(){
            super(TriggeringEvent.PR_OPEN)
        }

        PrBuilder addBranchRestriction(String branch){
            request.addBranchRestriction(branch)
            return this
        }
    }

    private class Builder {
        protected TriggeringEvent eventType
        protected Request request

        Builder(TriggeringEvent eventType){
            this.eventType = eventType
            this.request = null
        }

        protected Request getRequest(){
            if(request == null){
                request = new Request(eventType)
            }
            return request
        }

        Builder addBranchAsParameter(){
            getRequest().addBranchAsParameter()
            return this
        }

        Builder addShaAsParameter(){
            getRequest().addShaAsParameter()
            return this
        }

        Request build(){
            return request
        }
    }
}
