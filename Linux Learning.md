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

```java
rmdir [-p] 目录名称
```

选项和参数：

- -p： 从该目录起，一次删除多级空目录

删除runoob目录

```java
rmdir runoob/
```

将mkdir实例中创建的目录（/tmp 底下）删除掉！

```
[root@www tmp]# ls -l   <==看看有多少目录存在？
drwxr-xr-x  3 root  root 4096 Jul 18 12:50 test
drwxr-xr-x  3 root  root 4096 Jul 18 12:53 test1
drwx--x--x  2 root  root 4096 Jul 18 12:54 test2
[root@www tmp]# rmdir test   <==可直接删除掉，没问题
[root@www tmp]# rmdir test1  <==因为尚有内容，所以无法删除！
rmdir: `test1': Directory not empty
[root@www tmp]# rmdir -p test1/test2/test3/test4
[root@www tmp]# ls -l        <==您看看，底下的输出中test与test1不见了！
drwx--x--x  2 root  root 4096 Jul 18 12:54 test2
```

利用-p这个选项，立刻就可以将 test1/test2/test3/test4 一次删除

> 注意：**rmdir仅能删除空的目录，**可以使用rm命令来删除非空目录

### cp（复制文件或目录）

cp即拷贝文件和目录

```
[root@www ~]# cp [-adfilprsu] 来源档(source) 目标档(destination)
[root@www ~]# cp [options] source1 source2 source3 .... directory
```

选项和参数：

- -a：相当於 -pdr 的意思，至於 pdr 请参考下列说明
- -d:  若来源档为连结档的属性（link file），则复制连结档属性而非文件本身
- -f:  为强制（force）的意思，若目标文件已经存在且无法开启，则移除后再尝试一次
- -i: 若目标档（destination）已经存在时，在覆盖时会先询问动作的进行
- -l: 进行硬式连结（hard link）的连结档创建，而非复制文件本身
- -p: 连同文件的属性一起复制过去，而非使用默认属性（备份常用）
- -r: 递归持续复制，用于目录的复制行为
- -s: 复制成为符号连结档（symbolic link）
- -u: 若destination比source旧才升级destination

用root身份，将root目录下的 .bashrc 复制到 /tmp 下，并命名为bashrc

```
[root@www ~]# cp ~/.bashrc /tmp/bashrc
[root@www ~]# cp -i ~/.bashrc /tmp/bashrc
cp: overwrite `/tmp/bashrc'? n  <==n不覆盖，y为覆盖
```

### rm（移除文件或目录）

```
rm [-fir] 文件或目录
```

- -f: 就是force的意思，忽略不存在的文件，不会出现警告信息
- -i：互动模式，在删除前会询问使用者是否动作
- -r：递归删除

```
[root@www tmp]# rm -i bashrc
rm: remove regular file `bashrc'? y
```

如果加上-i的选项就会主动询问，避免删除到错误的档名

### mv（移动文件与目录，或者修改名称）

```
[root@www ~]# mv [-fiu] source destination
[root@www ~]# mv [options] source1 source2 source3 .... directory
```

- -f ：force 强制的意思，如果目标文件已经存在，不会询问而直接覆盖；
- -i ：若目标文件 (destination) 已经存在时，就会询问是否覆盖！
- -u ：若目标文件已经存在，且 source 比较新，才会升级 (update)

复制一文件，创建一目录，将文件移动到目录中

```
[root@www ~]# cd /tmp
[root@www tmp]# cp ~/.bashrc bashrc
[root@www tmp]# mkdir mvtest
[root@www tmp]# mv bashrc mvtest
```

将某个文件移动到某个目录去，就是这样做！

将刚刚的目录名称更名为 mvtest2

```
[root@www tmp]# mv mvtest mvtest2
```

## Linux文件内容查看

Linux系统中使用以下命令来查看文件的内容：

- cat 由第一行开始显示文件内容
- tac 从最后一行开始显示，可以看出 tac 是 cat 的倒着写！
- nl  显示的时候，顺道输出行号！
- more 一页一页的显示文件内容
- less 与 more 类似，但是比 more 更好的是，他可以往前翻页！
- head 只看头几行
- tail 只看尾巴几行

你可以使用 *man [命令]*来查看各个命令的使用文档，如 ：man cp



















