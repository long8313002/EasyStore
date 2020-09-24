排版地址：https://blog.csdn.net/long8313002/article/details/108772654

概述
          SharedPreferences作为android开发中最常用的持久化存储方案，非常适合属性和配置的本地存储（另外也可以使用本地文件、数据库的方式实现持久化）。虽然SharedPreferences的使用较为方便，但是维护起来确非常的麻烦，我们很容易定义出冗余的配置，也可能会生成大量的配置文件，甚至我们没有足够的信心来确定，定义的属性是否被重复定义（会导致隐晦bug）。现基于Kotlin来进行面向对象API的封装（使用到的技术有：类扩展、动态代理、注解等）。

 

使用
         

gradle中配置

  implementation 'com.zhangzheng.easystore:library:1.1.0'
 

Application初始化

 EasyStore.init(this)
       SharedPreferences的获取需要通过Context，因为考虑到使用的简便性，和后续的可扩展性，没有采用在使用的时候传递Context的方式。

 

Demo示例
定义数据结构接口
interface TestStorage :Storable{
    var name:String
    var count:Int
    var isBool:Boolean
}
      在TestStorage配置文件中，配置了三个属性，这里只需要定义接口，不需要具体的实现，用来描述存储的数据结构

 

获取数据
 val loadFromLocal = TestStorage::class.load()
 

获取单个数据
val name = TestStorage::class.get { name }
 

存储数据
TestStorage::class.apply {
            name = "2777777"
            count = 100
            isBool =false
        }
     

  TestStorage::class.commit {
            name = "2777777"
            count = 100
            isBool =false
        }
     apply是异步的，通过这种方式我们可以有选择的存储数据，比如如果只想存储name可以使用如下的方式：

      TestStorage::class.commit { name = "2777777" } ，其他属性并不会有变动。

 

扩展性
          实际开发中，我们不仅仅会将配置存储在SharedPreferences中，数据库和文件也是我们的选择方式之一，对于这种情况我们可以自己进行扩展，如下：

class TestStoreBuilder : IStoreBuilder {
 
    override fun build(storable: KClass<out Storable>, context: Context): IStore {
        return TestStore()
    }
 
    private class TestStore:IStore{
        override fun commit(values: Map<String, Any?>): Boolean {
        }
 
        override fun apply(values: Map<String, Any?>) {
        }
 
        override fun getAll(): Map<String, Any> {
        }
    }
 
}
使用方式
@Store(TestStoreBuilder::class)
interface TestStorage :Storable{
    var name:String
    var count:Int
    var isBool:Boolean
}
说明
        通过注解的方式来指定构造器，之所以采用这种方法来进行设计。一方面考虑通过抽象来屏蔽细节，开发者只需要定义配置数据结构而不需要关注实现，简化使用难度；另外注解也无法传递实例，所以这里借助无构造参数的Builder来创建存储策略。

 

最后
         如果这篇文章对您有用，希望大家可以关注和点赞！这里献上源代码：https://github.com/long8313002/EasyStore
