# <p align="center">Veteriner Yönetim Sistemi</p>


### Uygulama Detayları:
- Kullanıcılar, kliniğe gelen hayvanları ve hayvanların sahiplerini kaydedebilir. Bu sayede, hangi hayvanın kim tarafından getirildiğini depolayabilir ve gerekli değişiklikleri yapabilirler.
- Kullanıcılar, veritabanına doktorlar ekleyebilir ve doktorların uygun tarihleri temelinde randevular oluşturabilirler. Ayrıca, bu kayıtlar üzerinde değişiklikler yapabilirler.
- Kullanıcılar, hayvanlara uygulanan aşılar hakkında kayıt oluşturabilir ve expiring tarihlerini kontrol ederek aynı aşıların tekrar uygulanmasını önleyebilirler.

## UML Diyagramı
![Uml](https://github.com/user-attachments/assets/5c4455ba-7e11-45d2-8377-53fa0ea7e87c)

### Kullanılan Teknolojiler:
- Java 19
- Spring Boot
- Spring Data JPA
- PostgreSQL / MySQL
- Swagger UI
## Veritabanı

Sistem, aşağıdaki tabloları içeren ilişkisel bir veritabanı kullanır:

- `animals`: Hayvan bilgileri.
- `appointments`: Randevu detayları.
- `available_dates`: Doktorların randevu tarihleri.
- `customers`: Müşteri bilgileri.
- `doctors`: Doktor bilgileri.
- `vaccines`: Aşı bilgileri.

## API Dokümantasyonu

API dokümantasyonu Swagger kullanılarak sağlanmıştır. Swagger UI'yi kullanarak API uç noktalarını etkileşimli olarak keşfedebilir ve test edebilirsiniz.

- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Swagger, her uç noktanın kapsamlı açıklamasını sağlar ve şunları içerir:

- **Fonksiyon:** Uç noktanın işlevi ve sağladığı özellikler.
- **Girdi Parametreleri:** Bu uç noktayı çağırmak için gereken zorunlu ve isteğe bağlı parametreler.
- **Yanıt:** Dönen yanıtın formatı, durum kodları ve yanıt gövdesinin içeriği.


| Uç Nokta                                    | HTTP Metodu | Açıklama                                                         |
|---------------------------------------------|-------------|------------------------------------------------------------------|
| **animal-controller**                       |             |                                                                  |
| /v1/animal                                  | GET         | Tüm hayvanları alır.                                             |
| /v1/animal                                  | PUT         | Bir hayvanı günceller.                                           |
| /v1/animal                                  | POST        | Yeni bir hayvan ekler.                                           |
| /v1/animal/name/{animalName}                | GET         | Belirtilen adla hayvanı alır.                                    |
| /v1/animal/id/{id}                          | GET         | Belirtilen ID'ye sahip hayvanı alır.                             |
| /v1/animal/customer/{customerId}            | GET         | Belirtilen müşteri ID'si için hayvanları alır.                   |
| /v1/animal/{id}                             | DELETE      | Belirtilen ID'ye sahip hayvanı siler.                            |
|                                          |             |                                                                  |
| **appointment-controller**                  |             |                                                                  |
| /v1/appointment                             | GET         | Tüm randevuları alır.                                            |
| /v1/appointment                             | PUT         | Bir randevuyu günceller.                                         |
| /v1/appointment                             | POST        | Yeni bir randevu ekler.                                          |
| /v1/appointment/{id}                        | GET         | Belirtilen ID'ye sahip randevuyu alır.                           |
| /v1/appointment/{id}                        | DELETE      | Belirtilen ID'ye sahip randevuyu siler.                          |
| /v1/appointment/appointmentsByDateAndDoctor | GET         | Girdi tarih ve doktor ID'sine göre randevuları alır.             |
| /v1/appointment/appointmentsByDateAndAnimal | GET         | Girdi tarih ve hayvan ID'sine göre randevuları alır.             |
|                                          |             |                                                                  |
| **available-date-controller**               |             |                                                                  |
| /v1/availableDate                           | GET         | Tüm uygun tarihleri alır.                                        |
| /v1/availableDate                           | PUT         | Bir uygun tarihi günceller.                                      |
| /v1/availableDate                           | POST        | Yeni bir uygun tarih ekler.                                      |
| /v1/availableDate/{id}                      | GET         | Belirtilen ID'ye sahip uygun tarihi alır.                        |
| /v1/availableDate/{id}                      | DELETE      | Belirtilen ID'ye sahip uygun tarihi siler.                       |
|                                          |             |                                                                  |
| **customer-controller**                     |             |                                                                  |
| /v1/customer                                | GET         | Tüm müşterileri alır.                                            |
| /v1/customer                                | PUT         | Bir müşteriyi günceller.                                         |
| /v1/customer                                | POST        | Yeni bir müşteri ekler.                                          |
| /v1/customer/{id}                           | GET         | Belirtilen ID'ye sahip müşteriyi alır.                           |
| /v1/customer/{id}                           | DELETE      | Belirtilen ID'ye sahip müşteriyi siler.                          |
|                                          |             |                                                                  |
| **doctor-controller**                       |             |                                                                  |
| /v1/doctor                                  | GET         | Tüm doktorları alır.                                             |
| /v1/doctor                                  | PUT         | Bir doktoru günceller.                                           |
| /v1/doctor                                  | POST        | Yeni bir doktor ekler.                                           |
| /v1/doctor/{id}                             | GET         | Belirtilen ID'ye sahip doktoru alır.                             |
| /v1/doctor/{id}                             | DELETE      | Belirtilen ID'ye sahip doktoru siler.                            |
|                                          |             |                                                                  |
| **vaccine-controller**                      |             |                                                                  |
| /v1/vaccine                                 | GET         | Tüm aşıları alır.                                                |
| /v1/vaccine                                 | PUT         | Bir aşıyı günceller.                                             |
| /v1/vaccine                                 | POST        | Yeni bir aşı ekler.                                              |
| /v1/vaccine/{id}                            | GET         | Belirtilen ID'ye sahip aşıyı alır.                               |
| /v1/vaccine/{id}                            | DELETE      | Belirtilen ID'ye sahip aşıyı siler.                              |
| /v1/vaccine/expiring                        | GET         | Girdi tarih değerlerine göre yakında expiring olan aşıları alır. |
| /v1/vaccine/animal/{id}                     | GET         | Belirtilen hayvan ID'si için aşıları alır.                       |
