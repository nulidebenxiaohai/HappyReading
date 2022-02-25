# Java面向有哪一些特征

## 封装：说明一个类行为和属性和其他类的关系

封装（encapsulation。有时称为数据隐藏）是处理对象的一个重要概念。从形式上看，封装就是将数据和行为组合在一个包中，并对对象的使用者隐藏具体的实现方式。对象中的数据成为实例字段（instance field），操作数据的过程称为方法（method）。作为一个类的实例，特定对象都有一组特定的实例字段值。这些值就是这个对象的当前状态（state）。无论何时，只要在对象上调用一个方法，它的状态就有可能发生改变。

实现封装的关键在于，绝对不能让类中的方法直接访问其他类的实例字段。程序只能通过对象的方法对对象数据进行交互。使用者按照既定的方法调研方法，不必关心方法的内部实现，便于使用；便于修改，增强代码的可维护性。封装给对象赋予了“黑盒”特征，这是提高重用性和可靠性的关键。这意味着一个类可以完全改变存储数据的方式，只要依旧使用同样的方法操作数据，其他对象就不会直到也不用关心这个类所发生的变化。

## 继承：是父类和子类的关系

可以基于已有的类创建新的类。创建已经存在的类就是复用(继承)这些类的方法，而且可以增加一些新的方法和字段，使新类能够适应新的情况。在本质上是一般～特殊的关系。使用extends关键字继承了父类之后，实现类就具备了这些相同的属性。

父类通过private定义的变量和方法不会被继承，不能在子类中直接操作父类通过private定义的变量以及方法。继承避免了对一般类和特殊类之间共同特征进行的重复描述，通过继承可以清晰地表达每一项共同特征所适应的概念范围，在一般类中定义的属性和操作适应于这个类本身已经它以下的每一层特殊类的全部对象。

## 多态：是类与类之间的关系

封装和继承最后归于多态，多态指的是类与类之间的关系，两个类由继承，存在有方法的重写，故而可以在调用时有父类引用指向子类对象。多态必备三个要素：继承，重写和父类引用指向子类对象。



# Java 容器

## 一、概览

容器主要包括Collection和Map两种，Collection存储着对象的集合，而Map存储着键值对（key-value）的映射表。

### Collection

![Screenshot 2022-02-25 at 16.13.47](Java八股文.assets/Screenshot 2022-02-25 at 16.13.47.png)

#### 1. Set

- TreeSet：基于红黑树实现，支持有序操作。但是查找效率不如HashSet，HashSet查找的时间复杂度为O(1), TreeSet则为O(logN)。？？？为什么HashSet时间复杂度为O（1）？？？怎么用红黑树实现的？？？
- HashSet：基于哈希表实现，支持快速查找，但不支持有序性操作。并且失去了元素的插入顺序信息，也就是说使用Iterator遍历HashSet的到的结果是不确定的。？？？怎么用哈希表实现？？？
- LinkedHashSet：具有HashSet的查找效率，并且内部使用双向链表维护元素的插入顺序。？？？具体怎么实现的

#### 2. List

- ArrayList：基于动态数组实现，支持随机访问。
- Vector：和ArrayList类似，但是它是线程安全的。
- LinedList：基于双向链表实现，只能顺序访问，但是可以快速的在链表中间插入和删除元素。不仅如此，LinkedList哈可以作为栈，队列和双向队列。

### Map

![Screenshot 2022-02-25 at 16.28.49](Java八股文.assets/Screenshot 2022-02-25 at 16.28.49.png)

- TreeMap：基于红黑树实现。
- HashMap：基于哈希表实现。
- HashTable：和HashMap类似，但是它是线程安全的，这意味着同一时刻多个线程同时写入HashTabe不回导致数据不一致。它是遗留类，不应该去使用它，而是使用ConcurrentHashMap来支持线程安全，ConcurrentHashMap的效率会更高，因为ConcurrentHashMap引入了分段锁。？？？具体怎么实现，锁又是什么，怎么保证线程安全？？？

## 二、容器中的设计模式

### 迭代器模式

![Screenshot 2022-02-25 at 16.33.20](Java八股文.assets/Screenshot 2022-02-25 at 16.33.20.png)



Collection继承了Iterable接口，其中的iterator()方法能够产生一个Iterator对象，通过这个对象就可以迭代遍历Collection中的元素。从JDK1.5之后可以使用foreach方法来遍历Iterable借口的聚合对象。

```java
List<String> list = new ArrayList<>();
list.add("a");
list.add("b");
for(String item : list){
    System.out.println(item);
}
```

### 适配器模式

java.util.Arrays#asList()可以把数组类型转换为List类型。

```java
@SafeVarargs
public static <T> List<T> asList(T...a)
```

应该注意的是asList()的参数为**泛型**的变长参数，不能使用基本类型数组作为参数，只能使用相应的包装类型数组。

```java
integer[] arr = {1, 2, 3};
List list = Array.asList(arr);
```

也可以使用以下方式调用asList():

```java
List list = Arrays.asList(1,2,3);
```

## 三、源码分析

在IDEA中double shift调出Search EveryWhere，查找源码文件，找到后可以阅读源码。

### ArrayList

#### 1. 概览

因为ArrayList是基于数组实现的，所以支持快速随机访问。RandomAccess接口标识着该类支持快速随机访问。

```java
public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

数组的默认大小为10.

```java
private static final int DEFAULT_CAPACITY = 10;
```

![Screenshot 2022-02-25 at 16.52.10](Java八股文.assets/Screenshot 2022-02-25 at 16.52.10.png)



#### 2. 扩容

添加元素时使用ensureCapacityInternal()方法来保证容量足够，如果不够时，需要使用grow()方法进行扩容，新容量的大小为oldCapacity + (oldCapacity >> 1)， 即oldCapacity + oldCapacity/2。其中oldCapacity >> 1 需要取整，所以新的容量大约是旧的容量的1.5倍（oldCapacity为偶数时就是1.5倍，为奇数是就是1.5倍-0.5倍之间）。

扩容操作需要调用Arrays.copyOf()把原数组整个数组复制到新数组中。

```java
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}

private void ensureCapacityInternal(int minCapacity) {
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
    }
    ensureExplicitCapacity(minCapacity);
}

private void ensureExplicitCapacity(int minCapacity) {
    modCount++;
    // overflow-conscious code
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}

private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);//形成新的数组的长度
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    elementData = Arrays.copyOf(elementData, newCapacity);//把旧的数组的数组复制到新的数组里
}
```

#### 3. 删除元素

需要调用System.arraycopy()将index+1后面的元素都复制到index位置上，该操作的时间复杂度为O(n), 可以看到ArrayList删除元素的代价是非常高的。

```java
public E remove(int index){
    rangeCheak(index);
    modCount++;
    E oldValue = elementData(index);
    int numMoved = size - index - 1;
    if(numMoved > 0){
        System.arraycopy(elementData, index+1, elementData, index, numMoved);
    }
    elementData[--size] = null;
    return oldValue;
}
```

#### 4. 序列化？？？什么是序列化？？？

ArrayList



#### 5. Fail-Fast



### Vector

#### 1. 同步

它的实现与ArrayList类似，但是使用了synchronized进行同步。

```java
public synchronized boolean add(E e) {
    modCount++;
    ensureCapacityHelper(elementCount + 1);
    elementData[elementCount++] = e;
    return true;
}

public synchronized E get(int index) {
    if (index >= elementCount)
        throw new ArrayIndexOutOfBoundsException(index);

    return elementData(index);
}
```

#### 2. 扩容

Vector 的构造函数可以传入 capacityIncrement 参数，它的作用是在扩容时使容量 capacity 增长 capacityIncrement。如果这个参数的值小于等于 0，扩容时每次都令 capacity 为原来的两倍。

```java
public Vector(int initialCapacity, int capacityIncrement) {
    super();
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal Capacity: "+
                                           initialCapacity);
    this.elementData = new Object[initialCapacity];
    this.capacityIncrement = capacityIncrement;
}
```

```java
private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                                     capacityIncrement : oldCapacity);
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```

调用没有 capacityIncrement 的构造函数时，capacityIncrement 值被设置为 0，也就是说默认情况下 Vector 每次扩容时容量都会翻倍。

#### 3. 与ArrayList的比较

- Vector是同步的，因此开销就比ArrayList要大，访问速度更慢。最好使用ArrayList而不是Vector，因为同步操作完全可以由程序员自己来控制
- Vector每次扩容默认是其大小的2倍，而ArrayList是1.5倍

### CopyOnWriteArrayList



### LinkedList

#### 1. 概览

基于双向链表实现，使用Node存储链表节点信息

```java
private static class Node<E>{
    E item;
    Node<E> next;
    Node<E> prev;
}
```

每个链表存储了first和last指针：

```java
transient Node<E> first;
transient Node<E> last;
```

![Screenshot 2022-02-25 at 17.27.39](Java八股文.assets/Screenshot 2022-02-25 at 17.27.39.png)

#### 2. 与ArrayList的比较

ArrayList基于动态数组实现，LinkedList基于双向链表实现。ArrayList和LinkeList的区别可以归结于数组和链表的区别：

- 数组支持随机访问，但插入删除的代价很高，需要移动大量元素
- 链表不支持随机访问，但插入删除只需要改变指针

### HashMap

#### 1. 存储结构

内部包含了一个Entry类型的数组table。Entry存储着键值对。它包含了四个字段，从next字段我们可以看出Entry是一个链表。即数组中的每个位置被当成一个桶，一个桶存放一个链表。HashMap使用拉链法来解决冲突，同一个链表中存放哈希值和散列桶取模运算结果相同的Entry。 **？？？什么是拉链法？？？**

![Screenshot 2022-02-25 at 17.28.44](Java八股文.assets/Screenshot 2022-02-25 at 17.28.44.png)



```java
transient Entry[] table;
```

```java
static class Entry<K, V> implements Map.Entry<K, V>{
    final K key;
    V value;
    Entry<K, V> next;
    int hash;
  
    Entry(int h, K k, V v, Entry<K, V> n){
        value = v;
        next = n;
        key = k;
        hash = h;
    }
  
    public final K getKet(){
        return key;
    }
  
    public final V getValue(){
        return value;
    }
  
    public final V setValue(V newValue){
        V oldValue = value;
        value = new Value;
        return oldValue;
    }
  
    public final boolean equals(Object o){
        if(!(o instanceof Map.Entry))
            return false;
        Map.Entry e = (Map.Entry)o;
        Object k1 = getKey();
        Object k2 = e.getKey();
        if(k1 == k2 || (k1 != null && k1.equals(k2))){
            Object v1 = getValue();
            Object v2 = e.getValue();
            if(v1 == v2 || (v1 != null && v1.equals(v2)))
                retur true;
        }
        return false;
    }
  
    public final int hashCode(){
        return Objects.hashCode(getKey())^Objects.hashCode(getValue());
    }
  
    public final String toString(){
        return getKey() + "=" + getValue();
    }
}
```

#### 2. 拉链法的工作原理

```java
HashMap<String, String> map = new HashMap<>();
map.put("K1", "V1");
map.put("K2", "V2");
map.put("K3", "V3");
```

- 新建了一个HashMap，默认大小为16
- 插入<K1, V1>键值对，先计算K1的 hashCode为115，使用除留余数法的到所在桶下标115%16 = 3
- 插入<K2, V2>键值对，先计算K2的 hashCode为118，使用除留余数法的到所在桶下标118%16 = 6
- 插入<K3, V3>键值对，先计算K3的 hashCode为118，使用除留余数法的到所在桶下标118%16 = 6，插在<K2, V2>**前面**。

应该注意到链表的插入是以头插法方式进行的，例如上面的<K3, V3>不是插在<K2，V2>后面，而是插在链表头部。

查找需要分为两步进行：

- 计算键值对所在的桶；
- 在链表上顺序查找，时间复杂度显然和链表的长度成正比

![Screenshot 2022-02-25 at 20.31.29](Java八股文.assets/Screenshot 2022-02-25 at 20.31.29.png)

#### 3. put操作

```java
public V put(K key, V value){
    if(table == EMPTY_TABLE){
        inflateTable(threshold);
    }
    //键为null单独处理
    if(key == null){
        return putForNullKey(value);
    }
    int hash = hash(key);
    //确定桶下标
    int i = indexFor(hash, table.length);
    //先找出是否已经存在键为key的键值对，如果存在的话就更新这个键值对的值为value
    for(Entry<K, V> e = table[i]; e != null; e = e.next){
        Object k;
        if(e.hash == hash && ((k = e.key) == key || key.equals(k))){
            V oldValue = e.value;
            e.value = value;
            e.recordAccess(this);
            return oldValue;
        }
    }
    
    modCount++;
    //插入新键值对
    addEntry(hash, key, value, i);
    return null;
}
```

HashMap允许插入键为null的键值对。但是因为无法调用null的hashCode()方法，也就无法确定该键值对下的桶下标，只能通过强制制定一个桶下标来存放。HashMap使用第0个桶存放键null的键值对。

```java
private V putForNullKey(V value){
    for(Entry<K, V> e = table[0]; e != null; e = e.next){
        if(e.key == null){
            V oldValue = e.value;
            e.value = value;
            e.recordAccess(this);
            return oldValue;
        }
    }
    modCount++;
    addEntry(0, null, value, 0);
    return null;
}
```

使用链表的头插法，也就是新的键值对插在链表的头部，而不是链表的尾部。

```java
void addEntry(int hash, K key, V value, int bucketIndex) {
    if ((size >= threshold) && (null != table[bucketIndex])) {
        resize(2 * table.length);
        hash = (null != key) ? hash(key) : 0;
        bucketIndex = indexFor(hash, table.length);
    }

    createEntry(hash, key, value, bucketIndex);
}

void createEntry(int hash, K key, V value, int bucketIndex) {
    Entry<K,V> e = table[bucketIndex];
    // 头插法，链表头部指向新的键值对
    table[bucketIndex] = new Entry<>(hash, key, value, e);
    size++;
}
```

```java
Entry(int h, K k, V v, Entry<K,V> n) {
    value = v;
    next = n;
    key = k;
    hash = h;
}
```

#### 4. 确定桶下标

很多操作都需要先确定一个键值对所在的桶下标

```java
int hash = hash(key);
int i = indexFor(hash, table.length);
```

##### 4.1 计算hash值

```java
final int hash(Object k) {
    int h = hashSeed;
    if (0 != h && k instanceof String) {
        return sun.misc.Hashing.stringHash32((String) k);
    }

    h ^= k.hashCode();

    // This function ensures that hashCodes that differ only by
    // constant multiples at each bit position have a bounded
    // number of collisions (approximately 8 at default load factor).
    h ^= (h >>> 20) ^ (h >>> 12);
    return h ^ (h >>> 7) ^ (h >>> 4);
}
```

```java
public final int hashCode() {
    return Objects.hashCode(key) ^ Objects.hashCode(value);
}
```

##### 4.2 取模

另 x = 1 << 4, 即x为2的4次方，它具有以下性质

```java
x    : 00010000
x-1  : 00001111
```

另一个数y与x-1做与运算，可以去除y位级表示的第4位以上数：

```java
x       : 10110010
x-1     : 00001111
y&(x-1) : 00000010
```

这个性质和y对x取模效果是一样的：

```java
x       : 10110010
x-1     : 00010000
y%x     : 00000010
```

我们知道，位运算的代价比求模运算小的多，因此用这个计算时用位运算的话可以带来更高的性能。

```java
static int indexFor(int h, int length){
    return h & (length-1);
}
```

#### 5. 扩容-基本内容

设HashMap的table长度为M，需要存储的数值对数量为N，如果哈希函数满足均匀性的要求，那么每条链表的长度大约为N/M，因此查找的复杂度为O(N/M)。？？？？

为了让查找的成本降低，应该使N/M尽可能小，因此需要保证M尽可能大，也就是table尽可能大。HashMap采用动态扩容来根据当前的N值来调整M值，使得空间效率和时间效率都能得到保证。

| 参数       | 含义                                                         |
| ---------- | ------------------------------------------------------------ |
| capacity   | table的容量大小，默认为16。需要注意的是capacity必须保证为2的n次方。 |
| size       | 键值对数量                                                   |
| threshold  | size的临界值，当size大于等于threshold就必须进行扩容操作      |
| loadFactor | 装载因子，table能够使用的比例，threshold = (int) (capacity * loadFactor) |

```java
static final int DEFAULT_INITIAL_CAPACITY = 16;
static final int MAXIMUM_CAPACITY = 1 << 30;
static final float DEFAULT_LOAD_FACTOR = 0.75f;
transient Entry[] table;
transient int size;
int threshold;
final float loadFactor;
transient int modCount;
```

从下面的添加元素代码中可以看出，当需要扩容时，令capacity为原来的两倍。

```java
void addEntry(int hash, K key, V value, int bucketIndex){
    Entry<K, V> e = table[bucketIndex];
    table[bucketIndex] = new Entry<>(hash, key, value, e);
    if(size++ >= threshold){
        resize(2*table.length);
    }
}
```

扩容使用resize()实现，需要注意的是，扩容操作同样把oldTable的所有键值对重新插入newTable中，因此这一步是很费时的。

```java
void resize(int newCapacity) {
    Entry[] oldTable = table;
    int oldCapacity = oldTable.length;
    if (oldCapacity == MAXIMUM_CAPACITY) {
        threshold = Integer.MAX_VALUE;
        return;
    }
    Entry[] newTable = new Entry[newCapacity];
    transfer(newTable);
    table = newTable;
    threshold = (int)(newCapacity * loadFactor);
}

void transfer(Entry[] newTable) {
    Entry[] src = table;
    int newCapacity = newTable.length;
    for (int j = 0; j < src.length; j++) {
        Entry<K,V> e = src[j];
        if (e != null) {
            src[j] = null;
            do {
                Entry<K,V> next = e.next;
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            } while (e != null);
        }
    }
}
```

#### 6. 扩容-重新计算桶下标

在进行扩容时，需要把键值对重新计算桶下标，从而放到对应的桶上。在前面提到，HashMap使用hash%capaity来确定桶下标。HashMap capactiy为2的n次方这一特点能够极大降低重新计算桶下操作的复杂度。

假设原数组长度capacit为16，扩容为new capacity为32:

```java
capacity      : 00010000
new capacity  : 00100000
```

对于一个Key，他的哈希值hash在第5位：

- 为0，那么桶位置和原来一致
- 为1， hash%00010000 = hash%00100000 + 16，同位置是原位置 + 16

#### 7. 计算数组容量

HashMap 构造函数允许用户传入的容量不是 2 的 n 次方，因为它可以自动地将传入的容量转换为 2 的 n 次方。

//先考虑如何求一个数的掩码，对于 10010000，它的掩码为 11111111，可以使用以下方法得到：

#### 8. 链表转红黑树

从JDK1.8开始，一个桶存储的链表长度大于等于8时会将链表转换为红黑树。

#### 9. 与HashTable的比较

- HashTable使用synchronized来进行同步
- HashMap可以插入键为null的Entry
- HashMap的迭代器是fail-fast迭代器
- HashMap不能保证随着时间的推移Map中的元素次序是不变的 ？？？为什么？？？

### ConcurrentHashMap

#### 1. 存储结构

![Screenshot 2022-02-25 at 21.47.15](Java八股文.assets/Screenshot 2022-02-25 at 21.47.15.png)

```java
static final class HashEntry<K, V>{
    final int hash;
    final K key;
    volatile V value;
    
}
```













