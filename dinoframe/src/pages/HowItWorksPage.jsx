import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { ArrowLeft, Server, Database, Layout, Shield, Code, GitBranch, Cpu, Layers } from 'lucide-react';
import technicalHero from '@/assets/technical-hero.png';

export default function HowItWorksPage() {
    return (
        <div className="min-h-screen bg-slate-950 text-slate-300 font-sans selection:bg-primary/20">
            {/* Navbar */}
            <nav className="border-b border-white/5 bg-black/20 backdrop-blur-md sticky top-0 z-50">
                <div className="container mx-auto px-6 h-16 flex items-center justify-between">
                    <Link to="/" className="flex items-center gap-2 group">
                        <div className="bg-primary/10 p-2 rounded-lg group-hover:bg-primary/20 transition-colors">
                            <ArrowLeft className="h-5 w-5 text-primary" />
                        </div>
                        <span className="font-semibold text-white">Ana Sayfaya Dön</span>
                    </Link>
                    <div className="text-xl font-bold bg-gradient-to-r from-primary to-emerald-400 bg-clip-text text-transparent">
                        DinoBank Teknik Dokümantasyon
                    </div>
                </div>
            </nav>

            <main className="container mx-auto px-6 py-12 max-w-5xl">
                {/* Hero */}
                <header className="mb-20 text-center">
                    <div className="relative group perspective-1000">
                        <div className="absolute -inset-1 bg-gradient-to-r from-primary to-emerald-600 rounded-2xl blur opacity-20 group-hover:opacity-40 transition duration-1000 group-hover:duration-200"></div>
                        <img
                            src={technicalHero}
                            alt="DinoBank Technical Anatomy"
                            className="relative rounded-2xl shadow-2xl border border-white/10 w-full transform transition-transform duration-500 hover:scale-[1.01]"
                        />
                    </div>
                </header>

                {/* Section 1: Architecture Overview */}
                <section className="mb-24">
                    <div className="flex items-center gap-4 mb-8">
                        <div className="p-3 bg-blue-500/10 rounded-2xl">
                            <Cpu className="h-8 w-8 text-blue-400" />
                        </div>
                        <h2 className="text-3xl font-bold text-white">1. Sistem Mimarisi</h2>
                    </div>

                    <div className="bg-slate-900/50 border border-white/5 rounded-3xl p-8 mb-8 hover:border-blue-500/30 transition-colors">
                        <p className="mb-6 leading-loose">
                            DinoBank, modern bir <strong>Monolitik Mimari</strong> üzerine inşa edilmiştir ancak servis tabanlı bir yaklaşımla tasarlanmıştır.
                            Sistem, istemci (Frontend) ve sunucu (Backend) olmak üzere iki ana bileşenden oluşur ve
                            <strong>RESTful API</strong> prensipleriyle haberleşir.
                        </p>

                        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-8">
                            <div className="bg-black/40 p-6 rounded-2xl border border-white/5">
                                <h3 className="text-white font-bold mb-2 flex items-center gap-2">
                                    <Layout className="h-4 w-4 text-purple-400" /> Frontend (İstemci)
                                </h3>
                                <ul className="space-y-2 text-sm text-slate-400">
                                    <li>• React (Vite)</li>
                                    <li>• Tailwind CSS</li>
                                    <li>• Radix UI / Shadcn</li>
                                    <li>• Single Page App (SPA)</li>
                                </ul>
                            </div>

                            <div className="flex items-center justify-center">
                                <div className="h-0.5 w-full bg-slate-700 relative">
                                    <span className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 bg-slate-800 px-2 text-xs text-slate-500 font-mono">
                                        JSON / HTTP
                                    </span>
                                </div>
                            </div>

                            <div className="bg-black/40 p-6 rounded-2xl border border-white/5">
                                <h3 className="text-white font-bold mb-2 flex items-center gap-2">
                                    <Server className="h-4 w-4 text-emerald-400" /> Backend (Sunucu)
                                </h3>
                                <ul className="space-y-2 text-sm text-slate-400">
                                    <li>• Java 21</li>
                                    <li>• Spring Boot 3.x</li>
                                    <li>• Spring Security</li>
                                    <li>• PostgreSQL</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </section>

                {/* Section 2: Frontend Deep Dive */}
                <section className="mb-24">
                    <div className="flex items-center gap-4 mb-8">
                        <div className="p-3 bg-purple-500/10 rounded-2xl">
                            <Layout className="h-8 w-8 text-purple-400" />
                        </div>
                        <h2 className="text-3xl font-bold text-white">2. Frontend Detayları</h2>
                    </div>

                    <div className="space-y-6">
                        <div className="bg-slate-900/50 border border-white/5 rounded-3xl p-8">
                            <h3 className="text-xl font-bold text-white mb-4">Teknoloji Yığını & Yapı</h3>
                            <p className="mb-6">
                                Kullanıcı arayüzü, <strong>React</strong> kütüphanesi kullanılarak geliştirilmiştir.
                                Derleme aracı olarak <strong>Vite</strong> kullanılarak ışık hızında geliştirme deneyimi sağlanmıştır.
                                Tasarım dili olarak <strong>Tailwind CSS</strong> benimsenmiş, böylece tutarlı ve özelleştirilebilir bir "Dark Mode" arayüzü oluşturulmuştur.
                            </p>

                            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                                <div>
                                    <h4 className="font-bold text-slate-200 mb-3 border-b border-white/5 pb-2">📂 Dosya Yapısı</h4>
                                    <code className="text-sm font-mono text-blue-300 block bg-black/40 p-4 rounded-xl">
                                        dinoframe/<br />
                                        ├── src/<br />
                                        │   ├── components/ (Sidebar, UI kartları)<br />
                                        │   ├── pages/ (Dashboard, Login, Credit)<br />
                                        │   ├── lib/ (Yardımcı fonksiyonlar)<br />
                                        │   └── App.jsx (Ana Rota Yöneticisi)<br />
                                    </code>
                                </div>
                                <div>
                                    <h4 className="font-bold text-slate-200 mb-3 border-b border-white/5 pb-2">🎨 Temel Bileşenler</h4>
                                    <ul className="list-disc list-inside space-y-2 text-slate-400 text-sm">
                                        <li><strong className="text-white">Sidebar.jsx:</strong> Tüm sayfalarda bulunan navigasyon menüsü. Aktif sayfayı işaretler.</li>
                                        <li><strong className="text-white">DashboardPage.jsx:</strong> Kullanıcı bakiyesini, kartlarını ve işlem geçmişini özetler. "Dino Seviyesi" (Egg, Rex vb.) burada hesaplanır.</li>
                                        <li><strong className="text-white">CreditPage.jsx:</strong> Kredi başvurusu formu. Backend ile konuşarak anlık onay/red alır.</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                {/* Section 3: Backend Deep Dive */}
                <section className="mb-24">
                    <div className="flex items-center gap-4 mb-8">
                        <div className="p-3 bg-emerald-500/10 rounded-2xl">
                            <Server className="h-8 w-8 text-emerald-400" />
                        </div>
                        <h2 className="text-3xl font-bold text-white">3. Backend Detayları</h2>
                    </div>

                    <div className="space-y-6">
                        <div className="bg-slate-900/50 border border-white/5 rounded-3xl p-8">
                            <h3 className="text-xl font-bold text-white mb-4">Spring Boot Mimarisi</h3>
                            <div className="prose prose-invert max-w-none">
                                <p>
                                    Backend, klasik <strong>Katmanlı Mimari (Layered Architecture)</strong> prensiplerine sadık kalınarak geliştirilmiştir.
                                    Bu yapı, kodun bakımını ve test edilebilirliğini kolaylaştırır.
                                </p>
                            </div>

                            <div className="mt-8 space-y-4">
                                <div className="flex gap-4">
                                    <div className="w-32 shrink-0 font-mono text-xs text-right pt-1 text-slate-500">KATMAN 1</div>
                                    <div className="flex-1 bg-black/40 p-4 rounded-xl border-l-4 border-blue-500">
                                        <h4 className="font-bold text-blue-400">Controller (Denetleyici)</h4>
                                        <p className="text-sm mt-1">HTTP isteklerini karşılar (GET, POST). JSON formatında yanıt döner.</p>
                                        <div className="mt-2 text-xs font-mono text-slate-500">Örnek: CreditController.java, AuthController.java</div>
                                    </div>
                                </div>

                                <div className="flex gap-4">
                                    <div className="w-32 shrink-0 font-mono text-xs text-right pt-1 text-slate-500">KATMAN 2</div>
                                    <div className="flex-1 bg-black/40 p-4 rounded-xl border-l-4 border-purple-500">
                                        <h4 className="font-bold text-purple-400">Service (İş Mantığı)</h4>
                                        <p className="text-sm mt-1">Banka kurallarının işlediği yer. Kredi onayı, faiz hesabı burada yapılır.</p>
                                        <div className="mt-2 text-xs font-mono text-slate-500">Örnek: CreditService.java -&gt; applyForCredit()</div>
                                    </div>
                                </div>

                                <div className="flex gap-4">
                                    <div className="w-32 shrink-0 font-mono text-xs text-right pt-1 text-slate-500">KATMAN 3</div>
                                    <div className="flex-1 bg-black/40 p-4 rounded-xl border-l-4 border-emerald-500">
                                        <h4 className="font-bold text-emerald-400">Repository (Veri Erişimi)</h4>
                                        <p className="text-sm mt-1">Veritabanı ile konuşan katman. SQL sorguları yerine JPA metodları kullanılır.</p>
                                        <div className="mt-2 text-xs font-mono text-slate-500">Örnek: CustomerRepository.java, AccountRepository.java</div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div className="bg-slate-900/50 border border-white/5 rounded-3xl p-8">
                                <h3 className="text-xl font-bold text-white mb-4 flex items-center gap-2">
                                    <Shield className="h-5 w-5 text-red-400" /> Güvenlik (Security)
                                </h3>
                                <p className="text-sm text-slate-400 mb-4">
                                    <strong>Spring Security</strong> kullanılarak uçtan uca koruma sağlanmıştır.
                                </p>
                                <ul className="space-y-3 text-sm">
                                    <li className="flex gap-2">
                                        <span className="bg-red-500/20 text-red-300 px-2 py-0.5 rounded text-xs">BCrypt</span>
                                        <span>Şifreler veritabanında asla açık metin olarak saklanmaz, hashlenir.</span>
                                    </li>
                                    <li className="flex gap-2">
                                        <span className="bg-red-500/20 text-red-300 px-2 py-0.5 rounded text-xs">CORS</span>
                                        <span>Frontend ve Backend farklı portlarda çalışsa bile güvenli iletişim ayarlandı.</span>
                                    </li>
                                    <li className="flex gap-2">
                                        <span className="bg-red-500/20 text-red-300 px-2 py-0.5 rounded text-xs">Session</span>
                                        <span>Kullanıcı oturumları sunucu tarafında yönetilir.</span>
                                    </li>
                                </ul>
                            </div>

                            <div className="bg-slate-900/50 border border-white/5 rounded-3xl p-8">
                                <h3 className="text-xl font-bold text-white mb-4 flex items-center gap-2">
                                    <GitBranch className="h-5 w-5 text-yellow-400" /> Otomatik Veri (Seeding)
                                </h3>
                                <p className="text-sm text-slate-400 mb-4">
                                    Proje her başlatıldığında testlerin kolay yapılabilmesi için <strong>DataSeeder.java</strong> devreye girer.
                                </p>
                                <ul className="space-y-2 text-sm text-slate-400">
                                    <li>1. Osman, Kürşat vb. kullanıcıları var mı diye kontrol eder.</li>
                                    <li>2. Yoksa oluşturur ve hesaplarına varsayılan bakiye ekler.</li>
                                    <li>3. Örnek işlemler (Netflix, Kira, Maaş) ekleyerek Dashboard'u doldurur.</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </section>


                {/* Section 4: Database Logic */}
                <section className="mb-24">
                    <div className="flex items-center gap-4 mb-8">
                        <div className="p-3 bg-yellow-500/10 rounded-2xl">
                            <Database className="h-8 w-8 text-yellow-400" />
                        </div>
                        <h2 className="text-3xl font-bold text-white">4. Veritabanı Şeması</h2>
                    </div>

                    <div className="bg-slate-900/50 border border-white/5 rounded-3xl p-8 overflow-hidden">
                        <div className="flex flex-col md:flex-row gap-12">
                            <div className="flex-1 space-y-4">
                                <h3 className="text-xl font-bold text-white">Tablolar & İlişkiler</h3>
                                <p className="text-sm text-slate-400">
                                    PostgreSQL üzerinde koşan ilişkisel veritabanı yapımız 4 ana tablodan oluşur.
                                </p>

                                <div className="space-y-3 mt-6">
                                    <div className="p-4 bg-black/40 rounded-lg border border-white/5">
                                        <div className="font-mono text-yellow-400 font-bold">customers</div>
                                        <div className="text-xs text-slate-500 mt-1">id, ad, soyad, email, sifre, tc_kimlik</div>
                                    </div>
                                    <div className="flex justify-center -my-2"><div className="h-4 w-0.5 bg-slate-600"></div></div>
                                    <div className="p-4 bg-black/40 rounded-lg border border-white/5">
                                        <div className="font-mono text-yellow-400 font-bold">accounts</div>
                                        <div className="text-xs text-slate-500 mt-1">id, customer_id (FK), balance, currency</div>
                                    </div>
                                    <div className="flex justify-center -my-2"><div className="h-4 w-0.5 bg-slate-600"></div></div>
                                    <div className="grid grid-cols-2 gap-4">
                                        <div className="p-4 bg-black/40 rounded-lg border border-white/5">
                                            <div className="font-mono text-yellow-400 font-bold">transactions</div>
                                            <div className="text-xs text-slate-500 mt-1">from_account, to_account, amount</div>
                                        </div>
                                        <div className="p-4 bg-black/40 rounded-lg border border-white/5">
                                            <div className="font-mono text-yellow-400 font-bold">credit_apps</div>
                                            <div className="text-xs text-slate-500 mt-1">customer_id, amount, status</div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div className="flex-1 bg-black/20 p-6 rounded-2xl border border-white/5">
                                <h3 className="text-lg font-bold text-white mb-4">Örnek SQL Sorgusu</h3>
                                <pre className="font-mono text-xs text-blue-300 overflow-x-auto">
                                    {`-- Kredi Başvurusu Onay Mantığı
SELECT sum(balance) 
FROM accounts 
WHERE customer_id = 1;

-- Eğer (Toplam Bakiye * 4) >= İstenen Kredi
-- Kredi ONAYLANIR.
-- Para hesaba yatırılır:
UPDATE accounts 
SET balance = balance + 20000 
WHERE id = 5;`}
                                </pre>
                            </div>
                        </div>
                    </div>
                </section>

                {/* Footer Quote */}
                <div className="text-center py-20 border-t border-white/5">
                    <p className="text-2xl font-bold bg-gradient-to-r from-slate-200 to-slate-500 bg-clip-text text-transparent">
                        "Kodlamak bir sanat değil, modern bir hayatta kalma biçimidir."
                    </p>
                    <p className="text-sm text-slate-600 mt-4">- DinoBank Geliştirici Ekibi</p>
                </div>
            </main>
        </div>
    );
}
