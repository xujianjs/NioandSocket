package net;

import org.junit.Test;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Enumeration;
import java.util.List;

/**
 *
 *
 *
 * 主要介绍了 NetworkInterface 类、 InetAddress类 和 InterfaceAddress 类中常见方法
 * 的使用 这3个类主要获取的就是网络接口、 IP 地址及接口地址的相关信息 熟悉这3个
 * 类的基本使用是熟练掌握使用 Java 获取网络接口设备相关信息的前提
 *
 *
 * @author 徐健
 * @version 1.0.0
 * @ClassName NetworkDemo1.java
 * @Description TODO
 * @createTime 2019年09月03日 11:36:00
 */
public class NetworkDemo1 {
    @Test
    public void test1() throws SocketException {
        //获取本机的所有网络接口
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        //迭代
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            //获得网络设备名称
            String name = networkInterface.getName();
            //获得网络设备显示名称
            String displayName = networkInterface.getDisplayName();
            //是否已经开启并运行
            boolean up = networkInterface.isUp();
            //获取网络接口的索引
            int index = networkInterface.getIndex();
            //判断是否属于虚拟接口（相对于物理接口而言）
            boolean virtual = networkInterface.isVirtual();
            //每个NetworkInterface对应多个InterfaceAddress
            List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
            //便捷的获取绑定该网络接口的所有地址     每个InterfaceAddress对应一个InetAddress
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            //最大传输单元
            int mtu = networkInterface.getMTU();
            //获得网卡的物理地址
            byte[] hardwareAddress = networkInterface.getHardwareAddress();

            System.out.println("获得网络设备名称:"+name);
            System.out.println("获得网络设备显示名称:"+displayName);
            System.out.println("是否已经开启并运行:"+up);
            System.out.println("获取网络接口的索引:"+index);
            System.out.println("判断是否属于虚拟接口（相对于物理接口而言）:"+virtual);
            System.out.println("便捷的获取绑定该网络接口的所有地址:"+inetAddresses);
            System.out.println("最大传输单元:"+mtu);
            System.out.println("获得网卡的物理地址:");
            if (hardwareAddress != null && hardwareAddress.length != 0) {
                for (byte address : hardwareAddress) {
                    System.out.print(address + "");
                }
            }
            System.out.println();
        }

    }


}
