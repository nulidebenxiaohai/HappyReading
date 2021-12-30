[TOC]

# Linux 文件与目录管理

Linux的目录结构为树状结构，最顶级的目录为根目录 / 。

其他目录通过挂载可以将它们添加到树中，通过解除挂在可以移除他们。

- 绝对路径：

  路径的写法，由根目录 / 写起，例如： /usr/share/doc 这个目录

- 相对路径：

  路径的写法，不是通过 / 写起，例如由 /usr/share/doc 要到 /usr/share/man 底下时，可以写成： cd ../man 

## 处理目录的常用命令

- ls（list files）：列出目录及文件名
- cd（change directory）：切换目录
- pwd（print work directory）：显示目前的目录
- mkdir（make directory）: 创建一个新的目录
- rmdir（remove directory）：删除一个空的目录
- cp（copy file）：复制文件或目录
- rm（remove）：删除文件或目录
- mv（move file）：移动文件与目录，或修改文件与目录的名称

可以使用man[命令]来查看各个命令的使用文档，如： man cp

### ls（列出目录）

选项和参数：

- -a：全部的文件，连同隐藏文件（开头为 . 的文件）一起列出来
- -d：仅列出目录本身，而不是列出目录内的文件数据
- -l：长数据串列出，包含文件的属性与权限等等数据

将目录下的所有文件列出来（含属性与隐藏档）

```
ls -al ~
```

### pwd (显示目前所在的目录)

- -P：显示出确实的路径，而非使用连结（link）路径

实例显示出实际的工作目录，而非链接档本身的目录名

### mkdir（创建新目录）

```
mkdir [-mp] 目录名称
```

- -m: 配置文件的权限，不需要看默认权限（umask）
- -p：帮助你直接将所需要的目录（包含上一级目录）递归拆创建起来

```
[root@www ~]# cd /tmp
[root@www tmp]# mkdir test    <==创建一名为 test 的新目录
[root@www tmp]# mkdir test1/test2/test3/test4
mkdir: cannot create directory `test1/test2/test3/test4': 
No such file or directory       <== 没办法直接创建此目录啊！
[root@www tmp]# mkdir -p test1/test2/test3/test4
```

加了这个-p的选项后，可以自行帮你创建多层目录

```
[root@www tmp]# mkdir -m 711 test2
[root@www tmp]# ls -l
drwxr-xr-x  3 root  root 4096 Jul 18 12:50 test
drwxr-xr-x  3 root  root 4096 Jul 18 12:53 test1
drwx--x--x  2 root  root 4096 Jul 18 12:54 test2
```

上面的权限部分，如果没有加上-m来强制配置属性，系统会使用默认属性

如果使用-m，例如 -m 711来给予新的目录 drwx--x--x的权限

### rmdir（删除空的目录）











