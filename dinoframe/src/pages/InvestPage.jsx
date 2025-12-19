import { useState } from 'react';
import Sidebar from '@/components/Sidebar';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { TrendingUp, TrendingDown, DollarSign, Bitcoin, Activity } from 'lucide-react';
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

const data = [
    { name: '10:00', value: 4000 },
    { name: '11:00', value: 3000 },
    { name: '12:00', value: 5000 },
    { name: '13:00', value: 2780 },
    { name: '14:00', value: 1890 },
    { name: '15:00', value: 2390 },
    { name: '16:00', value: 3490 },
];

export default function InvestPage() {
    return (
        <div className="flex min-h-screen bg-background text-foreground">
            <div className="hidden md:block w-72 fixed h-full border-r border-white/5 bg-black/20 backdrop-blur-xl">
                <Sidebar />
            </div>

            <div className="flex-1 md:ml-72 p-8 overflow-y-auto">
                <header className="flex justify-between items-center mb-10">
                    <div>
                        <h1 className="text-4xl font-extrabold tracking-tight bg-gradient-to-r from-emerald-400 to-cyan-600 bg-clip-text text-transparent">Yatırım Dünyası</h1>
                        <p className="text-slate-400 mt-1">DinoCoin ve hisse senedi piyasalarını takip edin.</p>
                    </div>
                </header>

                <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-4 mb-8">
                    <Card className="bg-black/40 border-emerald-500/30">
                        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                            <CardTitle className="text-sm font-medium text-slate-200">DinoCoin (DNC)</CardTitle>
                            <Bitcoin className="h-4 w-4 text-emerald-500" />
                        </CardHeader>
                        <CardContent>
                            <div className="text-2xl font-bold text-white">$45,231.89</div>
                            <p className="text-xs text-emerald-500 flex items-center mt-1">
                                <TrendingUp className="h-3 w-3 mr-1" /> +20.1% bugün
                            </p>
                        </CardContent>
                    </Card>
                    <Card className="bg-black/40 border-white/10">
                        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                            <CardTitle className="text-sm font-medium text-slate-200">Portföy Değeri</CardTitle>
                            <DollarSign className="h-4 w-4 text-purple-500" />
                        </CardHeader>
                        <CardContent>
                            <div className="text-2xl font-bold text-white">$12,345.00</div>
                            <p className="text-xs text-purple-500 flex items-center mt-1">
                                <Activity className="h-3 w-3 mr-1" /> Stabil
                            </p>
                        </CardContent>
                    </Card>
                    <Card className="bg-black/40 border-red-500/30">
                        <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                            <CardTitle className="text-sm font-medium text-slate-200">Meteor Stocks</CardTitle>
                            <TrendingDown className="h-4 w-4 text-red-500" />
                        </CardHeader>
                        <CardContent>
                            <div className="text-2xl font-bold text-white">$1,203.45</div>
                            <p className="text-xs text-red-500 flex items-center mt-1">
                                <TrendingDown className="h-3 w-3 mr-1" /> -4.5% bugün
                            </p>
                        </CardContent>
                    </Card>
                </div>

                <Card className="bg-black/20 border-white/5">
                    <CardHeader>
                        <CardTitle className="text-slate-200">DinoCoin Performansı (24s)</CardTitle>
                    </CardHeader>
                    <CardContent className="h-[400px]">
                        <ResponsiveContainer width="100%" height="100%">
                            <AreaChart data={data}>
                                <defs>
                                    <linearGradient id="colorValue" x1="0" y1="0" x2="0" y2="1">
                                        <stop offset="5%" stopColor="#10b981" stopOpacity={0.3} />
                                        <stop offset="95%" stopColor="#10b981" stopOpacity={0} />
                                    </linearGradient>
                                </defs>
                                <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="rgba(255,255,255,0.05)" />
                                <XAxis dataKey="name" stroke="#64748b" />
                                <YAxis stroke="#64748b" prefix="$" />
                                <Tooltip
                                    contentStyle={{ backgroundColor: '#0f172a', border: '1px solid rgba(255,255,255,0.1)' }}
                                    itemStyle={{ color: '#fff' }}
                                />
                                <Area type="monotone" dataKey="value" stroke="#10b981" fillOpacity={1} fill="url(#colorValue)" />
                            </AreaChart>
                        </ResponsiveContainer>
                    </CardContent>
                </Card>
            </div>
        </div>
    );
}
