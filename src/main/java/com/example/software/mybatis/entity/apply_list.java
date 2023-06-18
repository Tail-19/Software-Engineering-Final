package com.example.software.mybatis.entity;
import com.example.software.mybatis.entity.apply;
import java.util.Vector;
public class apply_list {
    public Vector<apply> applylist;
    public apply_list()    //向申请队列中添加申请
    {
        this.applylist=new Vector<apply>();
    }
    public boolean add_apply(apply a)
    {
        int f=0,i=0;
        if(applylist.size()<6) //等候区最多6辆车
        {
            for(i=0;i<this.applylist.size();i++)
            {
                if(((apply) this.applylist.get(i)).getUserid()==a.getUserid())
                {
                    f=1;
                }
            }
        }
        if(f==1)
        {
            return false;
        }
        else
        {
            this.applylist.add(a);
            return true;
        }
    }
    public boolean change_apply(apply a)   //修改充电队列中的信息
    {
        int f=0,i=0;
        for(i=0;i<this.applylist.size();i++)
        {
            if(((apply) this.applylist.get(i)).getUserid()==a.getUserid())
            {
                this.applylist.set(i,a);
                f=1;
            }
        }
        if(f==1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean delete_apply_byid(int userid)
    {
        int f=0,i=0;
        for(i=0;i<this.applylist.size();i++)
        {
            if(((apply) this.applylist.get(i)).getUserid()==userid)
            {
                this.applylist.remove(i);
                f=1;
            }
        }
        if(f==1)
        {
            return  true;
        }
        else
        {
            return false;
        }
    }
    public boolean delete_apply_byindex(int index)
    {
        if(index<this.applylist.size())
        {
            this.applylist.remove(index);
            return true;
        }
        else
        {
            return false;
        }
    }
    public apply find_apply_byid(int userid)
    {
        //System.out.println("userid:"+userid);
        int i=0;
        apply result=null;
        for(i=0;i<this.applylist.size();i++)
        {
            if(((apply) this.applylist.get(i)).getUserid()==userid)
            {
                result=(apply) this.applylist.get(i);
            }
        }
        return result;
    }

    public int sparenum()
    {
        return (6-this.applylist.size());
    }
}
