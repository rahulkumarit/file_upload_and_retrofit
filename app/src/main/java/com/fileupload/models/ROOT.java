package com.fileupload.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by SONI on 10/4/2018.
 */

@Root(name = "ROOT")
public class ROOT {

    @Element(name = "T002")
    private T002 T002;

    public T002 getT002 ()
    {
        return T002;
    }

    public void setT002 (T002 T002)
    {
        this.T002 = T002;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [T002 = "+T002+"]";
    }


    }



