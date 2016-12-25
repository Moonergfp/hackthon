package com.hack.test;

public class Test {

   public  void test(){
      TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
      TbkItemGetRequest req = new TbkItemGetRequest();
      req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
      req.setQ("女装");
      req.setCat("16,18");
      req.setItemloc("杭州");
      req.setSort("tk_rate_des");
      req.setIsTmall(false);
      req.setIsOverseas(false);
      req.setStartPrice(10L);
      req.setEndPrice(10L);
      req.setStartTkRate(123L);
      req.setEndTkRate(123L);
      req.setPlatform(1L);
      req.setPageNo(123L);
      req.setPageSize(20L);
      TbkItemGetResponse rsp = client.execute(req);
      System.out.println(rsp.getBody());

   }

}
