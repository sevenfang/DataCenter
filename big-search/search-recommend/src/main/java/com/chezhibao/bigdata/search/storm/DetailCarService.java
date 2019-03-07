package com.chezhibao.bigdata.search.storm;

import java.util.List;

public interface DetailCarService {
    List<Integer> getSimilarDetail(int buyerId);
}
