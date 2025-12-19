import { useState } from 'react';
import Sidebar from '@/components/Sidebar';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Progress } from '@/components/ui/progress';
import { CreditCard, Plus, Shield, Zap, Lock } from 'lucide-react';

export default function CardsPage() {
    const [activeTab, setActiveTab] = useState('credit');

    return (
        <div className="flex min-h-screen bg-background text-foreground">
            <div className="hidden md:block w-72 fixed h-full border-r border-white/5 bg-black/20 backdrop-blur-xl">
                <Sidebar />
            </div>

            <div className="flex-1 md:ml-72 p-8 overflow-y-auto">
                <header className="flex justify-between items-center mb-10">
                    <div>
                        <h1 className="text-4xl font-extrabold tracking-tight bg-gradient-to-r from-purple-400 to-pink-600 bg-clip-text text-transparent">Kartlarım</h1>
                        <p className="text-slate-400 mt-1">Kartlarınızı yönetin ve limitlerinizi kontrol edin.</p>
                    </div>
                    <Button className="bg-white text-black hover:bg-slate-200">
                        <Plus className="mr-2 h-4 w-4" /> Yeni Kart Başvurusu
                    </Button>
                </header>

                <div className="grid gap-8 lg:grid-cols-2">
                    {/* Visual Card Display */}
                    <div className="space-y-6">
                        <div className="relative group perspective-1000">
                            <div className="relative h-56 w-96 rounded-2xl bg-gradient-to-r from-purple-600 to-blue-600 p-6 text-white shadow-2xl transition-transform transform group-hover:scale-105 duration-300">
                                <div className="flex justify-between items-start">
                                    <div className="text-2xl font-bold">DinoBank</div>
                                    <CreditCard className="h-8 w-8 opacity-80" />
                                </div>
                                <div className="mt-8 flex items-center gap-2">
                                    <div className="bg-white/20 px-2 py-1 rounded text-xs backdrop-blur-sm">CHIP</div>
                                    <Zap className="h-4 w-4 text-yellow-300" />
                                </div>
                                <div className="mt-4 text-2xl font-mono tracking-widest">
                                    4522 **** **** 9832
                                </div>
                                <div className="mt-6 flex justify-between items-end">
                                    <div>
                                        <div className="text-[10px] opacity-75 mb-1">KART SAHİBİ</div>
                                        <div className="font-bold tracking-wider text-sm truncate w-40">TEST USER</div>
                                    </div>
                                    <div>
                                        <div className="text-xs opacity-75">SKT</div>
                                        <div className="font-bold">12/28</div>
                                    </div>
                                    <div className="text-2xl font-bold italic opacity-50">PLATINUM</div>
                                </div>

                                {/* Shiny effect */}
                                <div className="absolute inset-0 rounded-2xl bg-gradient-to-tr from-white/10 to-transparent pointer-events-none"></div>
                            </div>
                        </div>

                        <div className="grid grid-cols-2 gap-4 w-96">
                            <Button variant="outline" className="border-white/10 hover:bg-white/5 text-white">
                                <Lock className="mr-2 h-4 w-4" /> Şifre Değiştir
                            </Button>
                            <Button variant="destructive" className="bg-red-900/50 hover:bg-red-900/80">
                                <Shield className="mr-2 h-4 w-4" /> Kartı Dondur
                            </Button>
                        </div>
                    </div>

                    {/* Limits and Details */}
                    <div className="space-y-6">
                        <Card className="bg-black/40 border-white/10">
                            <CardHeader>
                                <CardTitle className="text-white">Harcama Limitleri</CardTitle>
                                <CardDescription>Aylık harcama detaylarınız</CardDescription>
                            </CardHeader>
                            <CardContent className="space-y-6">
                                <div>
                                    <div className="flex justify-between text-sm mb-2 text-slate-300">
                                        <span>Kredi Kartı Limiti</span>
                                        <span className="font-bold">₺15,450 / ₺50,000</span>
                                    </div>
                                    <Progress value={33} className="h-2" indicatorClassName="bg-purple-500" />
                                </div>
                                <div>
                                    <div className="flex justify-between text-sm mb-2 text-slate-300">
                                        <span>İnternet Alışverişi</span>
                                        <span className="font-bold">₺2,300 / ₺10,000</span>
                                    </div>
                                    <Progress value={23} className="h-2" indicatorClassName="bg-blue-500" />
                                </div>
                                <div>
                                    <div className="flex justify-between text-sm mb-2 text-slate-300">
                                        <span>Nakit Avans</span>
                                        <span className="font-bold">₺0 / ₺5,000</span>
                                    </div>
                                    <Progress value={0} className="h-2" indicatorClassName="bg-green-500" />
                                </div>
                            </CardContent>
                        </Card>
                    </div>
                </div>
            </div>
        </div>
    );
}
