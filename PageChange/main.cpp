//
//  main.cpp
//  PageChange
//
//  Created by ZHENG QIN on 11/22/11.
//  Copyright 2011 NYU. All rights reserved.
//

#include<iostream.h>
int choose;       // 选 择 置 换 方 法
int PageOrder[100];   // 页 面 走 向
int Order=0;  // 页 面 计 数
int MaxPage; // 页 面 总 数
int MaxPhy;  // 物 理 块 总数
int count;  // 命 中 次 数
void Init();
void Fifo();
void Lru();
int Max(struct PageTable M[]);

struct PageTable  // 页 表 结 构 体
{
    int PageNomber;
    int PhyNomber;
    int Sta;  // 状 态 位
    int Visit;  // 访 问 位
    int Change;  // 改 变 位
};
struct PageTable p[10];// 最 多 同 时 进 入 1 0 个 页 表
int main()
{
    void Init();
    void Fifo();
    void Lru();
    Init();
    cout<<" 请 选 择 置 换 方 法"<<endl<<"1、FIFO 2、LRU"<<endl;
    cin>>choose;
    if(choose==1)
    {
        cout<< " 物 理 块 变 化 过 程:"<<endl;
        Fifo();
        cout<<endl;
        cout<<" 命 中 次 数："<<count<<endl;
    }
    else
        Lru();
    return 0;
}
void Init()
{
    cout<<" 请 输 入 页 表 长 度";
    cin>>MaxPage;
    for(int i=1;i<=MaxPage;i++)
    {
        p[i].PageNomber=i;
        p[i].PhyNomber=0;
        p[i].Change=0;
        p[i].Sta=0;
        p[i].Visit=0;
    }
    cout<<endl<<" 请 输 入 物 理 块 数";
    cin>>MaxPhy;
    cout<<" 请 输 入 页 面 走 向 以 0  结 束 "<<endl;
    int j=0;
    PageOrder[0]=1;
    while(PageOrder[j]!=0)
    {
        j++;
        Order++;
        cin>>PageOrder[j];
        if(j>99)
        {
            cout<<" 超过最大数量，请重新输入,以0结束！";
            continue;
        }
    }
}
void Fifo()
{
    int Max(struct PageTable M[]);
    struct PageTable i[10];//模拟物理块
    for(int j=0;j<MaxPhy;j++)
    {
        i[j].PageNomber=0;
        i[j].Visit=0;
    }
    int b=0;// 标志位，标记物理块已满
    for(int k=1;k<Order;k++)
    {
        if(b==1)//物理块满，进行页面置换
        {
            int a=0;// 标志位是否命中
            for(int m=0;m<MaxPhy;m++)//判断命中
            {
                if(i[m].PageNomber==PageOrder[k])
                {
                    a=1;
                    count++;
                    cout<<"命中"<<" ";
                    break;
                }
            }
            if(a==1)continue;//命中继续循环
            int Ma=Max(i);// 未命中选择时间最长的物理块进行置换
            cout<<" 替 换"<<Ma<<" ";
            i[Ma]=p[PageOrder[k]];
            for(int l=0;l<MaxPhy;l++)
                i[l].Visit++;
            continue;
        }
        for(int j=0;j<MaxPhy;j++)//页面写入空物理块
        {
            if(i[j].PageNomber==0)
            {
                i[j]=p[PageOrder[k]];
                cout<<"进入"<<" ";
                for(int l=0;l<=j;l++)
                    i[l].Visit++;
                if(j==MaxPhy-1)
                    b=b+1;
                break;
            }
        }
    }
}
void Lru()
{
}
int Max(struct PageTable M[])//返回最大值
{
    int temp,Max=0;
    temp=M[0].Visit;
    for(int j=1;j<MaxPhy;j++)
    {
        if(temp<M[j].Visit)
        {
            temp=M[j].Visit;
            Max=j;
        }
    }
    return(Max);
}