package com.chezhibao.bigdata.zipkin.dubbo.dubbo;

import com.alibaba.dubbo.rpc.Result;
import com.github.kristofa.brave.ClientResponseAdapter;
import com.github.kristofa.brave.KeyValueAnnotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by chenjg on 16/7/24.
 */
public class DubboClientResponseAdapter implements ClientResponseAdapter {

    private Result rpcResult ;

    private Exception exception;

    public DubboClientResponseAdapter(Exception exception) {
        this.exception = exception;
    }



    public DubboClientResponseAdapter(Result rpcResult) {
        this.rpcResult = rpcResult;
    }

    @Override
    public Collection<KeyValueAnnotation> responseAnnotations() {
        List<KeyValueAnnotation> annotations = new ArrayList<KeyValueAnnotation>();
        if(exception != null){
            KeyValueAnnotation keyValueAnnotation=  KeyValueAnnotation.create("exception",exception.getMessage());
            annotations.add(keyValueAnnotation);
        }else{
            if(rpcResult.hasException()){
                KeyValueAnnotation keyValueAnnotation=  KeyValueAnnotation.create("exception",rpcResult.getException().getMessage());
                annotations.add(keyValueAnnotation);
            }else{
                KeyValueAnnotation keyValueAnnotation=  KeyValueAnnotation.create("status","success");
                annotations.add(keyValueAnnotation);
            }
        }
        return annotations;
    }

}
