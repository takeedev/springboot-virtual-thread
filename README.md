# Virtual-thread ใน java 21

## Virtual Threads คืออะไร
#### เป็นฟีเจอร์ที่ปรับปรุงใน JAVA 21 ช่วยให้การทำงานของ Thread มีประสิทธืภาพมากขึ้น โดยการลด Overhead ที่เกิดจากการสร้างและจัดการ thread ของ OS level (plateform thread) ซึ่ง Virtual Thead จะช่วยให้การสร้างและจัดการ thread หลายตัวมีประสิทธิภาพมากขึ้น เข้ามาแทนที่ Platform Thread

## Virtual Threads และ platform thread ต่างกันอย่างไร
    
* Platform Thread จะถูกสร้างและจัดการโดย OS level ซึ่งจะมีการจำกัดของ OS
    * ใน 1 thread OS จะต้องทำงาน งานใดงานหนึ่งเสร็จก่อน ถึงจะทำงานถัดไปได้
* Virtual Thread จะถูกจัดการโดย JVM ซึ่งสามารถสร้างและจัดการได้หลายล้าน thread
    * ใน 1 thread OS ซึ่ง jvm สามารถสลับการทำงานใน virtual thread ได้อย่างรวดเร็ว บน platform thread โดยไม่จำเป็นรองานใดงานหนึ่งเสร็จก่อน

### เปรียบเทียบเป็นตาราง
คุณสมบัติ | Platform Threads | Virtual Threads
----|----|----|
การสร้าง Thread | ช้ากว่า (มี Overhead) และใช้ Memory เยอะ | เร็วกว่า (มี Overhead น้อย) และใช้ Memory น้อย
จำนวน Thread ที่รองรับ | มีข้อจำกัดจาก OS (หลักพัน) | สามารถสร้างได้จำนวนมาก (หลักล้าน)
Overhead | สูง (ใช้ทรัพยากรจาก OS) | ต่ำ (จัดการโดย Java Runtime)
การใช้งาน | เหมาะสำหรับงานที่มี Thread จำนวนน้อย | เหมาะสำหรับงานที่มี Thread จำนวนมาก
Context Switching | ทำโดย OS (ช้า) | ทำโดย JVM (เร็ว)
Blocking | ถ้า Thread ติด Block → รอเสร็จก่อน | ถ้า Thread ติด Block → JVM สลับไปทำงานอื่นทันที
เหมาะกับงานแบบไหน | งานที่ใช้ CPU หนักๆ (Parallel Processing) | งานที่ใช้ I/O สูงๆ (เช่น API, Database)

* [doc-oracle](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html#GUID-DC4306FC-D6C1-4BCC-AECE-48C32C1A8DAA)
* [block-medium-thai](https://medium.com/kbtg-life/%E0%B8%AA%E0%B8%A7%E0%B8%B1%E0%B8%AA%E0%B8%94%E0%B8%B5-virtual-threads-%E0%B8%99%E0%B8%B2%E0%B8%99-%E0%B9%86-%E0%B8%97%E0%B8%B5-java-%E0%B8%88%E0%B8%B0%E0%B8%A1%E0%B8%B5%E0%B8%82%E0%B8%AD%E0%B8%87%E0%B9%83%E0%B8%AB%E0%B9%89%E0%B8%95%E0%B8%B7%E0%B9%88%E0%B8%99%E0%B9%80%E0%B8%95%E0%B9%89%E0%B8%99-a1ee14310827)
* [block-medium-eng](https://medium.com/@coffeeandtips.tech/exploring-the-power-of-virtual-threads-in-java-21-29f83c88367c)


### Example code
