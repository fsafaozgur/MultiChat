									
Hazırlayan: Fatih Safa Özgür

# MultiChat Uygulaması

## Giriş
Proje kapsamında; 1 Server ve Client'lerden oluşan çok kullanıcılı bir mesajlaşma uygulaması, Java programlama dili ile gerçekleştirilmiştir.

## Hedef
Proje ile birlikte; Socket Programming ve MultiThread yapılar kullanılarak çok kullanıcılı, eş zamanlı mesajlaşma uygulamasının gerçekleştirilmesi sağlanmıştır.

## Çalışma Prensibi
Söz konusu sistemde; öncelikle Server uygulaması başlatılarak Admin tarafından girilen bir port numarası ile bir ServerSocket oluşturulup, bu socket dinlenmeye başlanmaktadır.
Client uygulaması ile bir kullanıcı bağlantı kurmak istediği zaman bu talep, ilgili ServerSocket'e gelmekte, Server bu talebe karşılık bir Thread oluşturarak söz konusu Client ile haberleşmeyi bu Thread yardımıyla yapmaktadır. Oluşturulan her bağlantı için ilgili Thread yardımıyla bir DataOutputStream objesi meydana getirilerek bu, Server tarafından önceden oluşturulmuş olan bir DataOutputStream listesine eklenmek suretiyle saklanmaktadır. Bu liste, bir bakıma Client yani kullanıcı listesidir.
Birden çok kullanıcı ile her biri için özel olarak oluşturulan birden çok Thread yardımıyla eş zamanlı olarak mesajlaşma işlemi gerçekleştirilmektedir.
Client uygulaması ile kullanıcı tarafından Server'a yollanılacak her mesaj, iletişim halinde olduğu Thread objesine yollanmakta, ilgili nesne almış olduğu mesajı daha önce Server tarafından oluşturulan DataOutputStream objelerini (kullanıcılar) içeren listedeki tüm objelere yani tüm Clientlere yollamaktadır.
Clientler ise Run() methodu ile sürekli olarak Server'dan (Server tarafından oluşturulan ilgili Thread objesi) gelen, Server kaynaklı mesajlar ya da diğer kullanıcıların yazdıkları mesajları dinleyerek, gelen mesajları kendi arayüzüne print işlemini gerçekleştirmektedir.
Yani bir Client tarafından gönderilen bir mesaj önce Server tarafından oluşturulmuş ilgili Thread objesine ulaşmakta, daha sonra bu obje yardımıyla DataOutputStream nesnelerini içeren listedeki tüm objelere (tüm kullanıcılara) gönderilmekte, haliyle Client'in yolladığı mesaj hem diğer Clientlere gitmekte hem de listede kendi de yer aldığı için aynı zamanda kendine geri dönmekte, Run() methodu yardımı ile de arayüzüne mesajı print etmektedir.
