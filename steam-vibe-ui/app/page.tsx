'use client';

import { useState } from 'react';

interface GameRecommendation {
  appId: string;
  name: string;
  currentPrice: number;
  originalPrice: number;
  discountPercentage: number;
  vibes: string[];
  matchReason: string;
}

export default function Home() {
  const [steamId, setSteamId] = useState('');
  const [recommendations, setRecommendations] = useState<GameRecommendation[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchRecommendations = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setRecommendations([]);

    try {
      const response = await fetch(`http://localhost:8080/api/recommendations/${steamId}`);
      const data = await response.json();
      console.log(data)
      if (!response.ok) {
        throw new Error(data.error || 'Failed to fetch recommendations');
      }

      setRecommendations(data);
    } catch (err: any) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100 p-8">
      <div className="max-w-4xl mx-auto space-y-8">
        
        {/* Header */}
        <div className="text-center space-y-2">
          <h1 className="text-4xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-purple-500">
            Steam Vibe Engine
          </h1>
          <p className="text-slate-400">Discover what to play next based on your library and vibes.</p>
        </div>

        {/* Search Bar */}
        <form onSubmit={fetchRecommendations} className="flex gap-4 max-w-xl mx-auto">
          <input
            type="text"
            placeholder="Enter your 17-digit Steam ID..."
            value={steamId}
            onChange={(e) => setSteamId(e.target.value)}
            className="flex-1 px-4 py-3 rounded-lg bg-slate-800 border border-slate-700 focus:outline-none focus:border-blue-500 transition-colors"
            required
          />
          <button
            type="submit"
            disabled={loading}
            className="px-6 py-3 bg-blue-600 hover:bg-blue-700 rounded-lg font-semibold transition-colors disabled:opacity-50"
          >
            {loading ? 'Scanning...' : 'Analyze'}
          </button>
        </form>

        {/* Error Message */}
        {error && (
          <div className="p-4 bg-red-900/50 border border-red-500 rounded-lg text-red-200 text-center">
            {error}
          </div>
        )}

        {/* Results Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 pt-8">
          {recommendations.map((game) => (
            <div key={game.appId} className="bg-slate-800 p-6 rounded-xl border border-slate-700 space-y-4 shadow-lg">
              <div className="flex justify-between items-start">
                <h2 className="text-xl font-bold">{game.name}</h2>
                <div className="text-right">
                  <p className="text-lg font-bold text-green-400">${game.currentPrice}</p>
                </div>
              </div>
              
              <p className="text-slate-400 text-sm italic">"{game.matchReason}"</p>
              
              <div className="flex flex-wrap gap-2">
                {game.vibes.map((vibe, index) => (
                  <span key={index} className="px-3 py-1 bg-slate-700 text-blue-300 text-xs rounded-full font-medium">
                    {vibe}
                  </span>
                ))}
              </div>
            </div>
          ))}
        </div>

      </div>
    </main>
  );
}