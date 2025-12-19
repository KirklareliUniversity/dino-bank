import { useState, useEffect } from 'react';
import Sidebar from '@/components/Sidebar';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { User, Bell, Shield, Moon } from 'lucide-react';

export default function SettingsPage() {
    const [user, setUser] = useState({ ad: '', soyad: '', email: '' });

    useEffect(() => {
        const userStr = localStorage.getItem('dinoUser');
        if (userStr) {
            setUser(JSON.parse(userStr));
        }
    }, []);

    return (
        <div className="flex min-h-screen bg-background text-foreground">
            <div className="hidden md:block w-72 fixed h-full border-r border-white/5 bg-black/20 backdrop-blur-xl">
                <Sidebar />
            </div>

            <div className="flex-1 md:ml-72 p-8 overflow-y-auto">
                <header className="mb-10">
                    <h1 className="text-4xl font-extrabold tracking-tight bg-gradient-to-r from-slate-200 to-slate-500 bg-clip-text text-transparent">Ayarlar</h1>
                    <p className="text-slate-400 mt-1">Hesap tercihlerinizi ve g??venlik ayarlarınızı yönetin.</p>
                </header>

                <div className="grid gap-6 max-w-2xl">
                    <Card className="bg-black/40 border-white/10">
                        <CardHeader>
                            <CardTitle className="flex items-center gap-2 text-white">
                                <User className="h-5 w-5" /> Profil Bilgileri
                            </CardTitle>
                            <CardDescription>Kişisel bilgilerinizi güncelleyin.</CardDescription>
                        </CardHeader>
                        <CardContent className="space-y-4">
                            <div className="grid grid-cols-2 gap-4">
                                <div className="space-y-2">
                                    <Label htmlFor="name" className="text-slate-300">İsim</Label>
                                    <Input id="name" defaultValue={user.ad} className="bg-slate-900 border-slate-700 text-white" />
                                </div>
                                <div className="space-y-2">
                                    <Label htmlFor="surname" className="text-slate-300">Soyisim</Label>
                                    <Input id="surname" defaultValue={user.soyad} className="bg-slate-900 border-slate-700 text-white" />
                                </div>
                            </div>
                            <div className="space-y-2">
                                <Label htmlFor="email" className="text-slate-300">E-posta</Label>
                                <Input id="email" defaultValue={user.email} className="bg-slate-900 border-slate-700 text-white" disabled />
                            </div>
                            <Button className="w-full bg-primary text-black font-bold hover:bg-primary/90">Değişiklikleri Kaydet</Button>
                        </CardContent>
                    </Card>

                    <Card className="bg-black/40 border-white/10">
                        <CardHeader>
                            <CardTitle className="flex items-center gap-2 text-white">
                                <Shield className="h-5 w-5" /> Güvenlik
                            </CardTitle>
                        </CardHeader>
                        <CardContent className="space-y-4">
                            <Button variant="outline" className="w-full border-slate-700 text-slate-300 hover:bg-slate-800">Şifre Değiştir</Button>
                            <Button variant="outline" className="w-full border-red-900/50 text-red-400 hover:bg-red-900/20 hover:text-red-300">Hesabı Sil</Button>
                        </CardContent>
                    </Card>
                </div>
            </div>
        </div>
    );
}
