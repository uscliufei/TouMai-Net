package com.runer.toumai.util;

import com.runer.toumai.net.RunnerParam;

/**
 * Created by ruier on 2018/7/18.
 */

public class ParamUtil {

    public interface  ParamINputCall{
      void   call(RunnerParam runnerParam);
    }

    public static RunnerParam generateRunparam(ParamINputCall paramINputCall){
        RunnerParam param =new RunnerParam() ;
        if (paramINputCall!=null){
            paramINputCall.call(param);
        }
        return  param ;
    }

}
