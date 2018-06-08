#coding:utf-8
"""将gbk编码的文件转成UTF-8编码格式的"""
__author__ = "halcyon"

import os,sys
import chardet

##多种编码转utf8
def convert(filename, in_enc = ["GBK","GB2312","GB18030","ASCII"],out_enc="utf8"):
    try:
        print "convert " + filename
        content = open(filename).read()#读取文件
        result = chardet.detect(content)#通过chardet.detect获取当前文件的编码格式串，返回类型为字典类型（如果已经确定自己的项目的编码格式，这两行可以无视了）
        coding = result.get("encoding") #获取encoding的值<编码格式>(同上)
        for k in in_enc:
            if coding == k:
                print coding + " to utf8"
                new_content = content.decode(coding).encode(out_enc)#原理：把文件先转到Unicode,再转到utf8
                open(filename,'w').write(new_content)#写入到本地原文件
                print "done."
                break;
    except IOError,e:
        print " error"

##遍历文件夹
def explore(dir):
    for root, dirs,files in os.walk(dir):
        for file in files:
            path = os.path.join(root,file)
            convert(path)


def main():
    for path in sys.argv[1:]:
        if os.path.isfile(path):
            convert(path)#如果传入的是文件，则直接转码
        elif os.path.isdir(path):
            explore(path)#如果传入参数是文件夹路径，则遍历转码<br>
##调用主方法<br>
if __name__ == "__main__":
	main();
