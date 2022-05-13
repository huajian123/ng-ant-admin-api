package util;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: examination
 * @description:
 * @author: fbl
 * @create: 2021-03-17 10:27
 **/
public class ListPageUtil {
    public static  PageInfo getPage(Integer page, Integer limit, List resultList) {
        //获取pageable的参数的页码，从1开始
        int pageNo = page;
        //获取pageable的页显示大小
        int pageSize = limit;

        int fromIndex = pageSize * (pageNo - 1);
        int toIndex = pageSize * pageNo;

        if (toIndex > resultList.size()) {
            toIndex = resultList.size();
        }
        if (fromIndex > toIndex) {
            fromIndex = toIndex;
        }
        if(pageNo != 0){
            //获取list的分页集合
            List result = resultList.subList(fromIndex, toIndex);
            //创建page对象，并且可以排序
            PageInfo resultPageInfo = new PageInfo<>(result);
            resultPageInfo.setTotal(resultList.size());
            return resultPageInfo;
        }else {
            PageInfo pageInfo = new PageInfo<>(resultList);
            pageInfo.setTotal(resultList.size());
            return pageInfo;
        }

    }

}
