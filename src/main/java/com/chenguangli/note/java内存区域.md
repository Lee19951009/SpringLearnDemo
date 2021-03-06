1.运行时数据区域有：方法区、虚拟机栈、本地方法栈、堆、程序计数器
  程序计数器:当前线程所执行的字节码的行号指示器
            是线程私有的
            如果执行的是java方法，则记录的是正在执行的虚拟机字节码指令的地址
            如果执行的是native方法，为空(undefined)
            不会出现OOM的区域
  
  虚拟机栈:  描述的是java方法执行的内存模型
            每个方法执行时会创建一个栈帧，存储局部变量表、操作数栈、动态链接、方法出口信息
            是线程私有的
            生命周期与线程相同
            会出现StackOverflow和outOfMemory
            
  本地方法栈:和虚拟机栈相似，为native方法服务
  
  堆:       线程共享
            可分为 新生代和老年代
            细分为 Eden空间、From Survivor空间、To Survivor空间
  
  方法区:    线程共享
            存储被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码数据
            运行时常量池 用于存放编译器生成的各种字面量和符号引用
2.直接内存
    可以理解为堆外内存
    不是虚拟机运行数据区的一部分，但被频繁使用，会导致OOM
    NIO是一种基于通道与缓冲区的I/O方式，使用了native函数直接分配堆外内存
        然后通过一个存储在java堆中的一个DirectByteBuffer对象作为这块内存
        的引用进行操作，显著的提高了性能，因为避免了在java堆和native堆中来回复制数据
    受本机总内存大小和寻址空间的限制，会出现OOM
    
3.在分配对象的过程如何解决线程安全的问题？
    一种是分配内存空间的动作进行同步处理，使用CAS+失败重试的方法保证更新操作的原子性
    一种是把内存分配的动作按照线程划分在不同的空间之中进行，即每一个线程在java堆中预先分配一小块内存，称为本地线程分配缓冲(Thread Local Allocation Buffer)TLAB, 某个线程分配内存先在自己线程的TLAB上分配，只有TLAB用完了然后分配新的TLAB时，需要同步锁定

4.为什么一个对象初始化后基本数据类型的字段有默认值？(int 初始化为0, boolean 初始化为 false)
    因为在内存分配完成后，虚拟机将分配到的内存空间都初始化为零值, 这一操作保证了对象的实例字段不用赋值就可以直接使用
    
5.对象栈上分配满足的条件
  1) 逃逸分析, new 出的对象没有被其他地方引用，只在本方法使用
  2) 标量替换, 对象的成员变量可以被替换(如一个对象有两个成员变量, int a, String b ,满足被替换直接放在栈上)

