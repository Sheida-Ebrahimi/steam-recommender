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

interface RecommendationResponse {
  recommended: GameRecommendation[];
  owned: GameRecommendation[];
}

export default function Home() {
  const [steamId, setSteamId] = useState('');
  const [vibe, setVibe] = useState('cozy');
  const [data, setData] = useState<RecommendationResponse | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchRecommendations = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setData(null);

    try {
      const response = await fetch(`http://localhost:8080/api/recommendations/${steamId}?vibe=${vibe}`);
      const result = await response.json();

      if (!response.ok) {
        throw new Error(result.error || 'Failed to fetch recommendations');
      }

      setData(result);
    } catch (err: any) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="min-h-screen bg-slate-900 text-slate-100 p-8">
      <div className="max-w-4xl mx-auto space-y-8">
        
        <div className="text-center space-y-2">
          <h1 className="text-4xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-purple-500">
            Steam Vibe Engine
          </h1>
        </div>

        <form onSubmit={fetchRecommendations} className="flex gap-4 max-w-xl mx-auto">
          <input
            type="text"
            placeholder="Steam ID..."
            value={steamId}
            onChange={(e) => setSteamId(e.target.value)}
            className="flex-1 px-4 py-3 rounded-lg bg-slate-800 border border-slate-700"
            required
          />
          <input
            type="text"
            placeholder="Desired Vibe"
            value={vibe}
            onChange={(e) => setVibe(e.target.value)}
            className="w-1/3 px-4 py-3 rounded-lg bg-slate-800 border border-slate-700"
            required
          />
          <button
            type="submit"
            disabled={loading}
            className="px-6 py-3 bg-blue-600 rounded-lg font-semibold"
          >
            {loading ? 'Scanning...' : 'Analyze'}
          </button>
        </form>

        {error && (
          <div className="p-4 bg-red-900/50 border border-red-500 rounded-lg text-red-200 text-center">
            {error}
          </div>
        )}

        {data && (
          <div className="space-y-12 pt-8">
            <section>
              <h2 className="text-2xl font-bold mb-6 border-b border-slate-700 pb-2">Recommended for You</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {data.recommended.map((game) => (
                  <div key={game.appId} className="bg-slate-800 rounded-xl overflow-hidden border border-blue-500/30 shadow-lg flex flex-col">
                    <img 
                      src={`https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/${game.appId}/header.jpg`}
                      alt={game.name}
                      className="w-full h-48 object-cover"
                      onError={(e) => {
                        (e.target as HTMLImageElement).src = 'https://placehold.co/600x400/1e293b/475569?text=No+Image+Available';
                      }}
                    />
                    <div className="p-6 flex-1 flex flex-col">
                      <div className="flex justify-between items-start">
                        <h3 className="text-xl font-bold">{game.name}</h3>
                        <p className="text-green-400 font-bold">${game.currentPrice}</p>
                      </div>
                      <div className="flex flex-wrap gap-2 mt-auto pt-4">
                        {game.vibes.map((v, i) => (
                          <span key={i} className="px-3 py-1 bg-slate-700 text-blue-300 text-xs rounded-full font-medium">{v}</span>
                        ))}
                      </div>
                    </div>
                  </div>
                ))}
                {data.recommended.length === 0 && <p className="text-slate-400">No new recommendations found for this vibe.</p>}
              </div>
            </section>

            <section>
              <h2 className="text-2xl font-bold mb-6 border-b border-slate-700 pb-2">Already in your Library</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {data.owned.map((game) => (
                  <div key={game.appId} className="bg-slate-900 rounded-xl overflow-hidden border border-slate-700 opacity-60 shadow-lg flex flex-col grayscale hover:grayscale-0 transition-all duration-300">
                    <img 
                      src={`https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/${game.appId}/header.jpg`}
                      alt={game.name}
                      className="w-full h-32 object-cover"
                      onError={(e) => {
                        (e.target as HTMLImageElement).src = 'https://placehold.co/600x400/0f172a/334155?text=Owned';
                      }}
                    />
                    <div className="p-4 flex-1 flex flex-col">
                      <h3 className="text-lg font-bold text-slate-400">{game.name}</h3>
                      <p className="text-slate-500 text-sm mt-1">{game.matchReason}</p>
                      <div className="flex flex-wrap gap-2 mt-auto pt-4">
                        {game.vibes.map((v, i) => (
                          <span key={i} className="px-3 py-1 bg-slate-800 text-slate-500 text-xs rounded-full">{v}</span>
                        ))}
                      </div>
                    </div>
                  </div>
                ))}
                {data.owned.length === 0 && <p className="text-slate-400">You do not own any games matching this vibe.</p>}
              </div>
            </section>
          </div>
        )}

      </div>
    </main>
  );
}