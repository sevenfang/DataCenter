package com.chezhibao.bigdata.zipkin.dubbo.dubbo;

import com.alibaba.dubbo.rpc.Result;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.ServerResponseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by chenjg on 16/7/24.
 */
public class DubboServerResponseAdapter implements ServerResponseAdapter {

    private Result rpcResult ;

    public DubboServerResponseAdapter(Result rpcResult) {
        this.rpcResult = rpcResult;
    }

    @Override
    public Collection<KeyValueAnnotation> responseAnnotations() {
        List<KeyValueAnnotation> annotations = new ArrayList<KeyValueAnnotation>();
        if(!rpcResult.hasException()){
            KeyValueAnnotation keyValueAnnotation=  KeyValueAnnotation.create("server_result","true");
            annotations.add(keyValueAnnotation);
        }else {
            KeyValueAnnotation keyValueAnnotation=  KeyValueAnnotation.create("exception",rpcResult.getException().getMessage());
            annotations.add(keyValueAnnotation);
        }
        return annotations;
    }
}
